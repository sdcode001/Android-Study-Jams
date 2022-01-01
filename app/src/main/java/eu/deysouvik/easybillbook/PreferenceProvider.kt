package eu.deysouvik.easybillbook

import android.content.Context

class PreferenceProvider(context: Context) {
    private val sharedPreference=context.getSharedPreferences("myPreferences",0)

    fun putBoolean(key:String,value:Boolean){
        sharedPreference.edit().putBoolean(key,value).apply()
    }

    fun getBoolean(key:String):Boolean{
        return sharedPreference.getBoolean(key,false)
    }

    fun putString(key:String,value:String){
        sharedPreference.edit().putString(key,value).apply()
    }

    fun getString(key:String):String?{
        return sharedPreference.getString(key,null)
    }

}