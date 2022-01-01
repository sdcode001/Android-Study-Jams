package eu.deysouvik.easybillbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.product_add_dialog.*

class add_new_product_activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_add_dialog)

        val product_name=findViewById<EditText>(R.id.new_product_name)
        val product_price=findViewById<EditText>(R.id.price)


        add_product.setOnClickListener {
            var name=""
            var price=""
            if(product_name.text.isNotEmpty()){
                name=product_name.text.toString()
            }
            if(product_price.text.isNotEmpty()){
                price=product_price.text.toString()
            }
            if(product_name.text.isEmpty()||product_price.text.isEmpty()){
                Toast.makeText(this, "Product name/price is empty", Toast.LENGTH_SHORT).show()
            }
            else{
                val product=Product()
                product.Name=name
                product.Price=price
                ProductList.productdbHandler.addProduct(this,product)
                product_name.text.clear()
                product_price.text.clear()
                finish()
            }
        }


        cancel_product.setOnClickListener {
            product_name.text.clear()
            product_price.text.clear()
            finish()
        }


    }









}