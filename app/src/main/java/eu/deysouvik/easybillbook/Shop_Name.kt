package eu.deysouvik.easybillbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_shop_name.*

class Shop_Name : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_name)

        val et_shop_name=findViewById<EditText>(R.id.et_shop_name)

        save.setOnClickListener {

            if(et_shop_name.text.isEmpty()){
                Toast.makeText(this, "Please enter shop name", Toast.LENGTH_SHORT).show()
            }
            else{
                try {

                    MainActivity.preferenceProvider.putString(Constants.KEY_SHOP_NAME,et_shop_name.text.toString())
                    MainActivity.preferenceProvider.putBoolean(Constants.KEY_SAVED,true)
                    MainActivity.Shop_NAME.text=et_shop_name.text.toString()
                    et_shop_name.text.clear()
                }catch (e:Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }

               finish()


            }
        }

        Cancel.setOnClickListener {
            et_shop_name.text.clear()
            finish()
        }
    }
}