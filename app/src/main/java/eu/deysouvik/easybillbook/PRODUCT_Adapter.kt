package eu.deysouvik.easybillbook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_details.view.*

class Product_Adapter(mCtx: Context,val products:ArrayList<Product>): RecyclerView.Adapter<Product_Adapter.ViewHolder>() {

    val mCtx=mCtx

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tv_name=itemView.product_name
        val tv_price=itemView.product_price
        val btn_delete=itemView.delete_btn
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Product_Adapter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.product_details,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(p0: Product_Adapter.ViewHolder, p1: Int) {
          val product=products[p1]
        p0.tv_name.text=product.Name
        p0.tv_price.text=product.Price

        if(p1%2==0){
            p0.itemView.setBackgroundColor(ContextCompat.getColor(mCtx,R.color.gray))
            p0.btn_delete.setBackgroundColor(ContextCompat.getColor(mCtx,R.color.gray))

        }

        p0.btn_delete.setOnClickListener {
           val product_id=product.Id
            if(ProductList.productdbHandler.deleteCustomer(product_id)){
               products.removeAt(p1)
                notifyItemRemoved(p1)
                notifyItemRangeChanged(p1,products.size)
            }
            else{
                Toast.makeText(mCtx, "Error Deleting!", Toast.LENGTH_SHORT).show()
            }
        }





    }



}