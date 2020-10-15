package com.example.gallery

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

interface ApiService {

//    companion object {
//        val baseUrl = "https://pixabay.com/"
//    }

    @GET("api/")
    fun getPhoto(@QueryMap params: HashMap<String, String>): Call<PhotoItem?>?

}