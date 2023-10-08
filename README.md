
# PizzaCheckout

App used to calculate the price of pizzas based on certain discount criteria. Written in 100% Kotlin and Native Android.

**Screenshot**
<div>  
<img src="https://raw.githubusercontent.com/athulantonynp/PizzaCheckout/main/screenshots/Screenshot%202023-10-07%20at%204.37.56%20PM.png" width="300px" />  
</div>  

**Tech stack.**
1. Jetpack Compose for the UI 📱
2. MVVM for the architecture. ⚙️
3. Coil for image loading 🖼️
4. Material Design for Jetpack Compose 👩‍🎨

**How to setup and run?**
1. Download and install latest stable Android studio
2. Sync the project and followup the prompt.
3. Press on build icon ▶️ in Android Studio while connected to a real device or emulator
4. That's it, App will run on your device once its built!


**Walkthrough Instructions**
- [ ]  If you are looking for the core business logic, Head out to `java/athul/pizza/checkout/data/CheckoutRepository.kt`
- [ ] UI component code is written in the package `java/athul/pizza/checkout/ui`
- [ ]  Test cases are found in `PizzaCheckout/app/src/test`


**A general flow of the app would be,**

UI <---> ViewModel (MainViewModel.kt) <--> Model / Repository (CheckoutRepository.kt &  JSON storage for data source (Hardcoded asset file for now) )

**UI:** It contains the UI Code. A composable function is a smallest unit of a UI, Also sends the user action to the ViewModel but does not receive the response back directly. All responses are recieved via flow APIs of viewmodel

**ViewModel**: It acts as a connection between the View and the business logic. Furthermore, it doesn't have any idea about which View it has to use as it does not possess a direct reference with the View. Hence, the ViewModel isn't aware of the view that it is interacting with.

**Model**: It represents the business logic and the data of an Application. It also consists of the business logic - local and remote data source, model classes, repository.

Feel free to raise any bugs or concerns as a Github issue!