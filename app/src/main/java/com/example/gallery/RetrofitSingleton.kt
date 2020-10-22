package com.example.gallery

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

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
interface ApiService {
    @GET("api/")
    fun getPhoto(@QueryMap params: HashMap<String, String>): Call<Pixabay?>?

}

