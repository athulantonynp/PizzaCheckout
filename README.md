# PizzaCheckout

App used to calculate the price of pizzas based on certain discount criteria. Written in 100% Kotlin and Native Android.

#Screenshot
<img src="https://yourimageshare.com/ib/ZgCUUkFKtl" />

Tech stack.
1. Jetpack Compose for the UI 📱
2. MVVM for the architecture. ⚙️
3. Coil for image loading 🖼️
4. Material Design for Jetpack Compose 👩‍🎨

--> If you are looking for the core business logic, Head out to `java/athul/pizza/checkout/data/CheckoutRepository.kt`
--> UI component code is written in the package `java/athul/pizza/checkout/ui`
--> Test cases are in `PizzaCheckout/app/src/test`

A general flow of the app would be,

UI <---> ViewModel (MainViewModel) <--> Repository (Checkout Repository) <--> JSON storage

Feel free to raise any bugs or concerns as a Github repo!