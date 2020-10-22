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
    //https://pixabay.com/api/?key=18406861-c6e0c4888076e3bb006248fd0=flower&per_page=100&page=5
    @GET("api/")
    fun getPhoto(@QueryMap params: HashMap<String, String>): Call<Pixabay?>?
    @GET("api/")
    fun getPhotoTest(@QueryMap params: HashMap<String, String>): Call<Response<Any>>?
}

