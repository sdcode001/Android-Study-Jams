# Android Jam Study
## Easy Bill Book


### Problem Statement:
In our daily life we see our local shops and university/college canteens maintain notebook to write customers bills/dues but sometimes its very difficult to find old bills    and keep all the past records. In this system sometimes customers are also get confused about their dues and then its become very problematic for both customer and shop        owner.Another problem is all shops dont have their own website where they can show all the products/food items avablible and price list to their customers.So here we can solve all the problems mentioned above using a mobile application which is very user friendly and effective.
    
### Proposed Solution :
This "Easy Bill Book" app can maintain all customers bills/payment/dues in database. user(shop owner) can easily register all customers with their names and phone numbers   and at the same time a notification SMS will sent to that customer.after registering customer shop owner can update their bill/due records when new payment is done and at the same time a notification SMS will sent to that customer on his/her registered phone number from shop owners phone mentioning shop name and in this way both customer and shop owner stay updated.if shop owner wants to remove a customes form list,he/she can easily do it using delete button and customer record gets deleted from database.Records of all the customers will show on the home page in listed row format.There is a option where shop owner can set his/her shop name on the app and also this will mentioned in all the SMS notifications.In addition there is a bell button  

![Slide1](https://user-images.githubusercontent.com/92887905/147879333-b5dc36bc-b1ac-4a69-8825-05fe67002368.JPG)

### Functionality & Concepts used :
Following are few android concepts used to achieve the functionalities in app :
#### LinearLayout:
all the activities uses LinearLayout to position buttons,textviews,edittexts and recyclerviews.
#### RecyclerView:
To show customer records,name and phone number recyclerview is used in vertical mode.Recyclerview is also used to show product list.
#### Adapters: 
RecyclerView Adapters are used to make connection between databases and recyclerviews.
#### SmsManager:
This api is used to send SMS from users phone to customers phone numbers.
#### Database:
Here two SQlite databases is used. one to store customer records and another for product lists.
#### SharedPreferences:
To store the shop name sharedpreferences is used here.A separate class is made for sharedpreferences and its methods.
#### AlertDialog: 
alertdialogs are used to update customers records,to alert about deletion,to alert about sending SMS.

