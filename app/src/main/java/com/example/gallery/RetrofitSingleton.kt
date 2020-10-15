package com.example.gallery

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSingleton private constructor(){

    private var myService : ApiService
    companion object {
        private var INSTANCE : RetrofitSingleton?=null

        fun getInstance() =
            INSTANCE?: synchronized(this) {
                RetrofitSingleton().also { INSTANCE = it }
            }
    }
    init {
            Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.wanandroid.com/")
            .build().apply {
                myService = create(ApiService::class.java)
            }
    }
    fun getService() : ApiService{
        return myService
    }
}

