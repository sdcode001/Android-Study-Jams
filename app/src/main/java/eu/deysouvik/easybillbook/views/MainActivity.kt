package eu.deysouvik.easybillbook.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.deysouvik.easybillbook.*
import eu.deysouvik.easybillbook.utils.Constants
import eu.deysouvik.easybillbook.utils.PreferenceProvider
import eu.deysouvik.easybillbook.utils.DBHandler
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    companion object{
        lateinit var dbHandler: DBHandler
        private val SMS_PERMISSION_CODE=1
        lateinit var preferenceProvider: PreferenceProvider
        lateinit var Shop_NAME:TextView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DBHandler(this,null,null,1)

        preferenceProvider = PreferenceProvider(this)

        Shop_NAME =findViewById(R.id.shop_name)

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE), SMS_PERMISSION_CODE)
        }

        if(preferenceProvider.getBoolean(Constants.KEY_SAVED)){
            shop_name.text= preferenceProvider.getString(Constants.KEY_SHOP_NAME)
        }

        viewCustomers()

        add_customer_btn.setOnClickListener {
            val i= Intent(this, AddCustomerActivity::class.java)
            startActivity(i)
        }

        list_btn.setOnClickListener {
                val intent=Intent(this, ProductList::class.java)
                startActivity(intent)

        }

    }


    private fun viewCustomers(){
        val customerlist= dbHandler.getCustomers(this)
        val adapter= CustomerAdapter(this,customerlist)
        val rv:RecyclerView=findViewById(R.id.my_recyclerview)
        rv.layoutManager=LinearLayoutManager(this, RecyclerView.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter=adapter
    }

    override fun onResume() {
        viewCustomers()
        super.onResume()
    }


    fun bell_btn(view: View){
        val customer_list= dbHandler.getCustomers(this)
        val alert_dialog=AlertDialog.Builder(this)
        alert_dialog.setTitle("Alert!")
        alert_dialog.setIcon(R.drawable.notify_icon)
        alert_dialog.setMessage("Are you sure to notify all customers about their payment details!!")
        var count=0
        alert_dialog.setPositiveButton("Yes") { _, _ ->
            for (i in customer_list) {
                if (i.customerDue != 0.0 || i.myDue != 0.0) {
                    send_sms(
                        i.customerName,
                        i.customerPhnNo.trim(),
                        i.customerDue.toString(),
                        i.myDue.toString()
                    )
                    count++
                }
            }
            Toast.makeText(
                this,
                "Payment details successfully sent to $count customers",
                Toast.LENGTH_SHORT
            ).show()

        }
        alert_dialog.setNegativeButton("No") { _, _ -> }
        alert_dialog.show()
    }



    private fun send_sms(name:String,number:String,customer_due:String,shop_due:String){
        var sms:String=""
        if(preferenceProvider.getBoolean(Constants.KEY_SAVED)){
            sms="Hello $name greetings from ${preferenceProvider.getString(Constants.KEY_SHOP_NAME)}. Its just a remainder that your total due is $customer_due rupees and our shops total due to you is $shop_due.Have a good day"
        }
        else{
            sms="Hello $name greetings from MY Shop. Its just a remainder that your total due is $customer_due rupees and our shops total due to you is $shop_due.Have a good day"
        }

        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(number,null,sms,null,null)
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    fun shop_name_btn(view: View){
        val intt=Intent(this, Shop_Name::class.java)
       startActivity(intt)
    }
}