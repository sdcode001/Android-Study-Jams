package eu.deysouvik.easybillbook

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHandler(context: Context, name:String?, factory:SQLiteDatabase.CursorFactory?, version:Int):
    SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION){

    companion object{
        private val DATABASE_NAME="MyData.db"
        private val DATABASE_VERSION=1

        val CUSTOMERS_TABLE_NAME="Customers"
        val COLUMN_CUSTOMERID="Customerid"
        val COLUMN_CUSTOMERNAME="Customername"
        val COLUMN_CUSTOMERPHNNO="Customerphnno"
        val COLUMN_CUSTOMERDUE="Customerdue"
        val COLUMN_MYDUE="mydue"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val CREATE_CUSTOMERS_TABLE:String=("CREATE TABLE $CUSTOMERS_TABLE_NAME ("+
                                          "$COLUMN_CUSTOMERID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                          "$COLUMN_CUSTOMERNAME TEXT,"+
                                           "$COLUMN_CUSTOMERPHNNO TEXT,"+
                                           "$COLUMN_CUSTOMERDUE DOUBLE DEFAULT 0,"+
                                           "$COLUMN_MYDUE DOUBLE DEFAULT 0)")
        db?.execSQL(CREATE_CUSTOMERS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    fun getCustomers(mCtx:Context):ArrayList<Customer>{
        val qry="Select * From $CUSTOMERS_TABLE_NAME"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)
        val customers=ArrayList<Customer>()

        if(cursor.count==0){
            Toast.makeText(mCtx, "No Customer Records Found!", Toast.LENGTH_SHORT).show()
        }
        else{
            while(cursor.moveToNext()){
                val customer=Customer()
                customer.customerID=cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMERID).toInt())
                customer.customerName=cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMERNAME).toInt())
                customer.customerPhnNo=cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMERPHNNO).toInt())
                customer.customerDue=cursor.getDouble(cursor.getColumnIndex(COLUMN_CUSTOMERDUE).toInt())
                customer.myDue=cursor.getDouble(cursor.getColumnIndex(COLUMN_MYDUE).toInt())
                customers.add(customer)
            }
            Toast.makeText(mCtx, "${cursor.count.toString()} Records Found", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return customers
    }


    fun addCustomer(mCtx: Context,customer: Customer){
        val values=ContentValues()
        values.put(COLUMN_CUSTOMERNAME,customer.customerName)
        values.put(COLUMN_CUSTOMERPHNNO,customer.customerPhnNo)
        values.put(COLUMN_CUSTOMERDUE,customer.customerDue)
        values.put(COLUMN_MYDUE,customer.myDue)
        val db=this.writableDatabase

        try{
           db.insert(CUSTOMERS_TABLE_NAME,null,values)

        }catch (e:Exception){
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()

    }


    fun deleteCustomer(customerID:Int):Boolean{
        val qry="Delete From $CUSTOMERS_TABLE_NAME where $COLUMN_CUSTOMERID = $customerID"
        val db=this.writableDatabase
        var result:Boolean=false
        try {
            val cursor=db.execSQL(qry)
            result=true
        }catch (e: Exception){
            Log.e(ContentValues.TAG,"Error Deleting!")
        }
        db.close()
        return result
    }

    fun updateCustomer(id:String,name:String,phn_no:String,customer_due:String,my_due:String):Boolean{
        var result=false
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(COLUMN_CUSTOMERNAME,name)
        contentValues.put(COLUMN_CUSTOMERPHNNO,phn_no)
        contentValues.put(COLUMN_CUSTOMERDUE,customer_due.toDouble())
        contentValues.put(COLUMN_MYDUE,my_due.toDouble())
        try {
            db.update(CUSTOMERS_TABLE_NAME,contentValues,"$COLUMN_CUSTOMERID = ?", arrayOf(id))
            result=true
        }catch (e:Exception){
            Log.e(ContentValues.TAG,"Error Updating!")
        }
        return result
    }









}