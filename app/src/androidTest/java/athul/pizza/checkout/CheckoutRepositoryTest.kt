package athul.pizza.checkout
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import athul.pizza.checkout.data.CheckoutRepository
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckoutRepositoryTest {

    private lateinit var checkoutRepository: CheckoutRepository

    @Before
    fun setUp() {
        // Initialize the CheckoutRepository with the Application context
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        checkoutRepository = CheckoutRepository(app)
    }

    @Test
    fun testFetchProductsData() {
        // Verify that fetchProductsData() returns a non-null ProductData object
        val productData = checkoutRepository.fetchProductsData()
        assertNotNull(productData)
    }

    @Test
    fun testGetTotalAmountToBuyItem() {
       /* // Create sample data for testing
        val itemId = 1
        val count = 3
        val discountGroup = "GroupA"

        // Calculate the expected result based on the logic in getTotalAmountToBuyItem
        val discountDetail = DiscountDetail(
            discountGroup,
            DISCOUNT_TYPE_GET_X_FOR_Y,
            DiscountData(forQuantity = 2, getQuantity = 1)
        )
        val productData = ProductData(
            listOf(Product(1, 10.0)),
            listOf(discountDetail)
        )
        checkoutRepository.productsData = productData

        // Call getTotalAmountToBuyItem() and compare the result with the expected BuyData
        val result = checkoutRepository.getTotalAmountToBuyItem(itemId, count, discountGroup)
        val expectedTotalAmount = 20 // Expected total amount after applying the discount
        val expectedBuyData = BuyData(totalAmountToBuy = expectedTotalAmount, totalPizzas = count)
        assertEquals(expectedBuyData, result)*/
    }
}