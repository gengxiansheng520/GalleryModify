package com.example.gallery

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSingleton private constructor(){

    companion object {
        private var INSTANCE : RetrofitSingleton?=null
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        private val myService : ApiService = retrofit.create(ApiService::class.java)
        fun getInstance() =
            INSTANCE?: synchronized(this) {
                RetrofitSingleton().also { INSTANCE = it }
            }
    }

    fun getService() : ApiService{
        return myService
    }
}

