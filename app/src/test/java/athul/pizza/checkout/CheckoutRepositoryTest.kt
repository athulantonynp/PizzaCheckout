package athul.pizza.checkout
import athul.pizza.checkout.data.CheckoutRepository
import athul.pizza.checkout.data.DISCOUNT_TYPE_GET_X_FOR_Y
import athul.pizza.checkout.data.DISCOUNT_TYPE_PRICE_DROP
import athul.pizza.checkout.data.models.Data
import athul.pizza.checkout.data.models.DiscountData
import athul.pizza.checkout.data.models.Product
import athul.pizza.checkout.data.models.ProductData
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class CheckoutRepositoryTest {

    private lateinit var checkoutRepository: CheckoutRepository

    @Before
    fun setUp() {
        // Initialize the CheckoutRepository with the Application context
        val app = RuntimeEnvironment.getApplication()
        checkoutRepository = CheckoutRepository(app)
    }

    @Test
    fun testFetchProductsData() {
        // Verify that fetchProductsData() returns a non-null ProductData object
        val productData = checkoutRepository.fetchProductsData()
        assertNotNull(productData)
    }

    @Test
    fun `testGetTotalAmountToBuyItem with price drop type`() {
        checkoutRepository.productsData = ProductData(
            products = listOf(Product(1,"test","description",10.0,"$","")),
            discountData = listOf(DiscountData(DISCOUNT_TYPE_PRICE_DROP,"GROUP_1", listOf(1),data = Data(droppedAmount = 2L, forQuantity = null, getQuantity = null)))
        )

        val result1 = checkoutRepository.getTotalAmountToBuyItem(1,2,"GROUP_1")
        assert(result1?.totalAmountToBuy == 16.0)
        assert(result1?.discountMessage != null)

        val result2 = checkoutRepository.getTotalAmountToBuyItem(1,2,"DEFAULT")
        assert(result2?.totalAmountToBuy == 20.0)
        assert(result2?.discountMessage == null)
    }

    @Test
    fun `testGetTotalAmountToBuyItem with get x for y type`(){
        checkoutRepository.productsData = ProductData(
            products = listOf(Product(1,"test","description",11.99,"$","")),
            discountData = listOf(DiscountData(DISCOUNT_TYPE_GET_X_FOR_Y,"GROUP_1", listOf(1),data = Data(forQuantity = 2, getQuantity = 3)))
        )

        val result1 = checkoutRepository.getTotalAmountToBuyItem(1,3,"GROUP_1")
        assert(result1?.totalAmountToBuy == 23.98)
        assert(result1?.discountMessage != null)

        val result2 = checkoutRepository.getTotalAmountToBuyItem(1,3,"DEFAULT")
        assert(result2?.totalAmountToBuy == 35.97)
        assert(result2?.discountMessage == null)
    }

    @Test
    fun `testGetTotalAmountToBuyItem with empty or zero count`(){
        checkoutRepository.productsData = ProductData(
            products = listOf(Product(1,"test","description",11.99,"$","")),
            discountData = listOf(DiscountData(DISCOUNT_TYPE_GET_X_FOR_Y,"GROUP_1", listOf(1),data = Data(forQuantity = 2, getQuantity = 3)))
        )

        val result = checkoutRepository.getTotalAmountToBuyItem(1,0,"GROUP_1")
        assert(result == null)
    }
}