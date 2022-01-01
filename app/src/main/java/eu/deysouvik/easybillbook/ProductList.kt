package eu.deysouvik.easybillbook

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductList : AppCompatActivity() {


    companion object{
        lateinit var productdbHandler: Product_DBHandler
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        productdbHandler=Product_DBHandler(this,null,null,1)

        viewProducts()

    }

    private fun viewProducts(){
        val products= productdbHandler.getProducts(this)
        val Adpater=Product_Adapter(this,products)
        val rv:RecyclerView=findViewById(R.id.rv_productlist)
        rv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter=Adpater
    }

    override fun onResume() {
        viewProducts()
        super.onResume()
    }

    fun add_btn(view: View){
        val inte=Intent(this,add_new_product_activity::class.java)
        startActivity(inte)
    }

   fun notify_btn(view:View){
      val alert_dialog=AlertDialog.Builder(this)
       alert_dialog.setTitle("Alert!")
       alert_dialog.setIcon(R.drawable.notify_icon)
       alert_dialog.setMessage("Are you sure to notify all customers about Product list!!")
       alert_dialog.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->
           send_notification()
       })
       alert_dialog.setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->  })
       alert_dialog.show()
   }


    fun send_notification(){
        val customers=MainActivity.dbHandler.getCustomers(this)
        val products= productdbHandler.getProducts(this)
        var sms="Welcome to My Shop.Products available-->"
        if(MainActivity.preferenceProvider.getBoolean(Constants.KEY_SAVED)){
            sms="Welcome to ${MainActivity.preferenceProvider.getString(Constants.KEY_SHOP_NAME)}.Products available-->"
        }
        for(i in products){
            sms+="(${i.Name}-${i.Price}),"
        }
        for(i in customers){
            send_sms(sms,i.customerPhnNo.trim(),customers.size.toString())
        }
    }


    private fun send_sms(sms:String,number:String,no_customers:String){

        try {

            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(number,null,sms,null,null)
            Toast.makeText(this, "Product list sent to $no_customers customers", Toast.LENGTH_SHORT).show()
        }catch(e:Exception){
            e.printStackTrace()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }


    }







}