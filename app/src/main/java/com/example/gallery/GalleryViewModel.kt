package com.example.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import java.util.*

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive : LiveData<List<PhotoItem>>
    get() = _photoListLive
    private val map = HashMap<String, String>()
    fun fetchDataNew() {
        val key = "18406861-c6e0c4888076e3bb006248fd0"
        val random = Random()
        map.put("key", key)
        //map.put("q", q)
        map.put("per_page", "100")
        //map.put("pretty", "true")
        RetrofitSingleton.getInstance().getService().getPhoto(map)?.enqueue(object : Callback<PhotoItem?> {
            override fun onResponse(
                call: Call<PhotoItem?>,
                response: retrofit2.Response<PhotoItem?>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<PhotoItem?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun fetchData() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                _photoListLive.value = Gson().fromJson(it, Pixabay::class.java).hits.toList()
            },
            Response.ErrorListener {
                Log.d("hello", it.toString())
            }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }

    private fun getUrl():String {
        return "https://pixabay.com/api/?key=12472743-874dc01dadd26dc44e0801d61&q=${keyWords.random()}&per_page=100"
    }

    private val keyWords = arrayOf(
        "cat",
        "dog",
        "car",
        "beauty",
        "phone",
        "computer",
        "flower",
        "animal"
    )
}