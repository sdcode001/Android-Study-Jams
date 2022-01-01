package eu.deysouvik.easybillbook

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_add_customer2.*

class AddCustomerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer2)


        add.setOnClickListener {
            if(name.text.isEmpty() || phn_no.text.isEmpty() || phn_no.text.toString().length!=10){
                Toast.makeText(this, "Enter Name and Phone Number Correctly and dont use (+91)", Toast.LENGTH_LONG).show()
                name.requestFocus()
                phn_no.requestFocus()
            }
            else{
                val customer=Customer()
                customer.customerName=name.text.toString()
                customer.customerPhnNo=phn_no.text.toString().trim()
                if(customer_due.text.isEmpty()){
                    customer.customerDue=0.0 }
                else{
                    customer.customerDue=customer_due.text.toString().toDouble() }
                if(your_due.text.isEmpty()){
                    customer.myDue=0.0 }
                else{
                    customer.myDue=your_due.text.toString().toDouble() }

                MainActivity.dbHandler.addCustomer(this,customer)

                send_sms(customer.customerName,customer.customerPhnNo,customer.customerDue.toString(),customer.myDue.toString())

                clearEditsTexts()
                name.requestFocus()
                phn_no.requestFocus()
                finish()
            }
        }

        cancel.setOnClickListener {
            clearEditsTexts()
            finish()
        }

    }

    private fun clearEditsTexts(){
        name.text.clear()
        phn_no.text.clear()
        customer_due.text.clear()
        your_due.text.clear()
    }


    private fun send_sms(name:String,number:String,customer_due:String,shop_due:String){
        var sms=""
        if(MainActivity.preferenceProvider.getBoolean(Constants.KEY_SAVED)){
            sms="Hello $name Welcome to ${MainActivity.preferenceProvider.getString(Constants.KEY_SHOP_NAME)}.Your due is $customer_due rupees.And our shops due to you is $shop_due.Thank you for shopping with us"
        }
        else{
            sms="Hello $name Welcome to My Shop.Your due is $customer_due rupees.And our shops due to you is $shop_due.Thank you for shopping with us"
        }


        try {

            val smsManager:SmsManager=SmsManager.getDefault()
            smsManager.sendTextMessage(number,null,sms,null,null)
            Toast.makeText(this, "Notification sent to $name", Toast.LENGTH_SHORT).show()
        }catch(e:Exception){
            e.printStackTrace()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }


    }



}