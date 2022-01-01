package eu.deysouvik.easybillbook

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast


class Product_DBHandler(context: Context, name:String?, factory: SQLiteDatabase.CursorFactory?, version:Int):
    SQLiteOpenHelper(context,Database_NAME,factory,Database_VERSION){

    companion object{
        private val Database_NAME="MyProductData.db"
        private val Database_VERSION=1

        val PRODUCT_TABLE_NAME="Customers"
        val COLUMN_PRODUCTID="Productid"
        val COLUMN_PRODUCTNAME="Productname"
        val COLUMN_PRODUCTPRICE="Productprice"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val CREATE_PRODUCT_TABLE=("CREATE TABLE $PRODUCT_TABLE_NAME ("+
                                 "$COLUMN_PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                 "$COLUMN_PRODUCTNAME TEXT,"+
                                 "$COLUMN_PRODUCTPRICE TEXT )")
        db?.execSQL(CREATE_PRODUCT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    fun getProducts(mCtx:Context):ArrayList<Product>{
        val Products=ArrayList<Product>()
        val qry="Select * From $PRODUCT_TABLE_NAME"
        val db=this.readableDatabase
        val cursor=db.rawQuery(qry,null)

        if(cursor.count==0){
            Toast.makeText(mCtx, "No Products Found!", Toast.LENGTH_LONG).show()
        }
        else{
            while(cursor.moveToNext()){
                val product=Product()
                product.Id=cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCTID).toInt())
                product.Name=cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTNAME).toInt())
                product.Price=cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTPRICE).toInt())
                Products.add(product)
            }
            Toast.makeText(mCtx, "${cursor.count.toString()} Products Found", Toast.LENGTH_LONG).show()
        }
        cursor.close()
        db.close()
        return Products
    }



    fun addProduct(mCtx: Context,product: Product){
        val values= ContentValues()
        values.put(COLUMN_PRODUCTNAME,product.Name)
        values.put(COLUMN_PRODUCTPRICE,product.Price)

        val db=this.writableDatabase

        try{
            db.insert(PRODUCT_TABLE_NAME,null,values)

        }catch (e:Exception){
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()

    }


    fun deleteCustomer(productID:Int):Boolean{
        val qry="Delete From $PRODUCT_TABLE_NAME where $COLUMN_PRODUCTID = $productID"
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


}