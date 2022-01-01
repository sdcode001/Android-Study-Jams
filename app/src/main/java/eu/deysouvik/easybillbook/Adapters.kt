package eu.deysouvik.easybillbook

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.costomer_details.view.*

class CustomerAdapter(mCtx:Context,val customers:ArrayList<Customer>): RecyclerView.Adapter<CustomerAdapter.ViewHolder>(){

    val mCtx=mCtx

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val tv_name=itemView.name
        val tv_phn_no=itemView.phn_no
        val tv_customer_due=itemView.customer_due
        val tv_your_due=itemView.your_due
        val btn_update=itemView.update_btn
        val btn_delete=itemView.delete_btn

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomerAdapter.ViewHolder {
        val v =LayoutInflater.from(p0.context).inflate(R.layout.costomer_details,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    override fun onBindViewHolder(p0: CustomerAdapter.ViewHolder, p1: Int){
       val customer:Customer=customers[p1]
        p0.tv_name.text=customer.customerName
        p0.tv_phn_no.text=customer.customerPhnNo
        p0.tv_customer_due.text=customer.customerDue.toString()
        p0.tv_your_due.text=customer.myDue.toString()

        if(p1%2==0){
            p0.itemView.setBackgroundColor(ContextCompat.getColor(mCtx,R.color.lite_gray))
            p0.btn_delete.setBackgroundColor(ContextCompat.getColor(mCtx,R.color.lite_gray))
            p0.btn_update.setBackgroundColor(ContextCompat.getColor(mCtx,R.color.lite_gray))
        }

        p0.btn_delete.setOnClickListener {
            val customer_name=customer.customerName
            val alertDialog=AlertDialog.Builder(mCtx)
            alertDialog.setTitle("Warning")
            alertDialog.setIcon(R.drawable.warning_icon)
            alertDialog.setMessage("Are you sure to delete the record of $customer_name ?")
            alertDialog.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which->

                if(MainActivity.dbHandler.deleteCustomer(customer.customerID)){
                    customers.removeAt(p1)
                    notifyItemRemoved(p1)
                    notifyItemRangeChanged(p1,customers.size)
                    Toast.makeText(mCtx, "Records of $customer_name deleted", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(mCtx, "Error Deleting!", Toast.LENGTH_SHORT).show()
                }
            })
            alertDialog.setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->   })
            alertDialog.show()
        }


       p0.btn_update.setOnClickListener {
           val inflater=LayoutInflater.from(mCtx)
           val view=inflater.inflate(R.layout.update_dialog,null)

           val new_due=view.findViewById<EditText>(R.id.new_due)
           val paid=view.findViewById<EditText>(R.id.paid)
           val your_new_due=view.findViewById<EditText>(R.id.your_new_due)
           val paid_to_customer=view.findViewById<EditText>(R.id.paid_to_customer)

           var total_due=customer.customerDue
           var total_my_due=customer.myDue


           val builder=AlertDialog.Builder(mCtx)
           builder.setTitle("Update Customer Info..")
           builder.setView(view)
           builder.setPositiveButton("Update",DialogInterface.OnClickListener { dialog, which ->
               var paid_amount=0.0
               var new_due_amount=0.0
               var your_new_due_amount=0.0
               var i_paid_to_customer=0.0
               if(new_due.text.isNotEmpty()){
                   new_due_amount=new_due.text.toString().toDouble()
               }
               total_due=total_due+new_due_amount
               if(paid.text.isNotEmpty()){
                   paid_amount=paid.text.toString().toDouble()
               }
               if((total_due-paid_amount)>=0.0){
                   total_due=total_due-paid_amount
               }
               else{
                   total_my_due+=paid_amount-total_due
                   total_due=0.0
               }
               if(your_new_due.text.isNotEmpty()){
                   your_new_due_amount=your_new_due.text.toString().toDouble()
               }
               total_my_due=total_my_due+your_new_due_amount
               if(paid_to_customer.text.isNotEmpty()){
                   i_paid_to_customer=paid_to_customer.text.toString().toDouble()
               }
               if(total_my_due-i_paid_to_customer>=0.0){
                   total_my_due-=i_paid_to_customer
               }
               else{
                   total_due+=i_paid_to_customer-total_my_due
                   total_my_due=0.0
               }



               val isUpdate:Boolean=MainActivity.dbHandler.updateCustomer(customer.customerID.toString(),
                                                                          customer.customerName,
                                                                          customer.customerPhnNo,
                                                                          total_due.toString(),
                                                                          total_my_due.toString())
               if(isUpdate){
                   customers[p1].customerName=customer.customerName
                   customers[p1].customerPhnNo=customer.customerPhnNo
                   customers[p1].customerDue=total_due
                   customers[p1].myDue=total_my_due
                   notifyDataSetChanged()
                   Toast.makeText(mCtx, "Updated Successfully", Toast.LENGTH_SHORT).show()
                   new_due.text.clear()
                   paid.text.clear()
                   your_new_due.text.clear()



                       send_sms(customer.customerName,customer.customerPhnNo.trim(),paid_amount.toString(),new_due_amount.toString(),total_due.toString(),total_my_due.toString(),i_paid_to_customer.toString())



               }
               else{
                   Toast.makeText(mCtx, "Failed to update!", Toast.LENGTH_SHORT).show()
                   new_due.text.clear()
                   paid.text.clear()
                   your_new_due.text.clear()
               }

           })
           builder.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->
               new_due.text.clear()
               paid.text.clear()
               your_new_due.text.clear()
           })

           val update_dialog=builder.create()
           update_dialog.show()
       }


    }

    private fun send_sms(name:String,number:String,paid:String,new_due:String,customer_due:String,shop_due:String,shop_pay_you:String){
        var sms:String="You paid $paid rupees & your new due amount is $new_due rupees and shop pays you $shop_pay_you So currently Your total due is $customer_due rupees and Shops total due to you is $shop_due."

        try {

            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(number,null,sms,null,null)
            Toast.makeText(mCtx, "Bill update sent to $name", Toast.LENGTH_SHORT).show()
        }catch(e:Exception){
            e.printStackTrace()
            Toast.makeText(mCtx, e.message.toString(), Toast.LENGTH_SHORT).show()
        }


    }



}