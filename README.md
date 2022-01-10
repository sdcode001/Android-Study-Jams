
# Android Study Jams
## Easy Bill Book App


### Problem Statement:
We have seen lots of local shops and university/college canteens maintaining notebooks for their customers' bills/dues. But it becomes very difficult to find the old bills and keep all the past records. Moreover, the customers also tend to get confused about their dues and this creates a lot of trouble. Besides, all the shops don't even have their own apps/websites where they can display all their product menu. It is where our "Easy Bill Book" app comes to the rescue with a solution which is not only user-friendly, but free of cost as well!

### Proposed Solution :
This "Easy Bill Book" app helps to maintain the bills/payments/dues for all the customers in one place. The shop owners can easily add/remove the customers from the due list. Whenever any customer makes any payment, the shop owners can update his bill/due records, and automatically, a SMS notification will be sent to that particular customer on his/her registered phone number. This helps both the customer and shop owner  to stay updated on dues very easily. There is also a button on home screen through which the shop owner can send a SMS notification including the payment details and dues to all customers, just in a single click!

This app also provides another amazing feature for the product list. There is a separate screen displaying the list of all the products available in the shop, where the shop owners can easily maintain their products details. Whenever any new product arrives at the shop, an automatic SMS notification is sent to all customers with the updated product list.

![Slide1](https://user-images.githubusercontent.com/92887905/147879333-b5dc36bc-b1ac-4a69-8825-05fe67002368.JPG)

### Functionality & Concepts used :
Following are few android concepts used to achieve the functionalities in app :
- **LinearLayout**: Most of the activities use LinearLayout to position various view on the screen precisely.

- **RecyclerView**:   To display the records of the customer dues and product list.

- **AlertDialog**:   It is used to display alerts before deleting customers records and before sending SMS alerts to all the customers

- **SmsManager**:   This android api is used to send SMS notifications to all the registered customers.

- **SharedPreferences**:   To store  various details of the shop

- **MVVM** This app is built entirely using MVVM(Model-View-ViewModel) architecture

- **LiveData**:   To update the UI whenever any state inside the viewmodel changes. It ensures that the UI is always up to date with the latest data

- **Jetpack Navigation Component**:   It is used to navigate between various screens

- **Room database**:   It is used for storing and managing all the customer and the product details internally in a structured format. It mainly helps in data persistence.


### Application Link & Future Scope :
Here is our apk [Easy-Bill-Book-App.apk](https://github.com/sdcode001/Android-Study-Jams/releases/download/v1.0.0/app-release.apk).

This app is currently in the testing phase. Once it is fully tested, we plan to propose this app idea to our campus canteens and the neighbouring shops, and collaborate with them on this easy bill maintaing service. Coming to the future prospects, we are planning to integrate the Firebase database and push notifications in our next subsequent releases. We will also be working on improving the UI/UX to provide much better and smoother user experience across the entire app.
