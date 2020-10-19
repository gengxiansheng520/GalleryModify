package com.example.gallery

import android.util.Log
import androidx.paging.PageKeyedDataSource
import retrofit2.Call
import retrofit2.Callback
import uk.co.senab.photoview.log.LoggerDefault
import java.util.HashMap

class PixabayDataSource:PageKeyedDataSource<Int,PhotoItem>() {
    private val keyWords = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal").random()
    private var map = HashMap<String, String>()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoItem>
    ) {
        getMap(1)
        RetrofitSingleton.getInstance().getService().getPhoto(map).enqueue(object :
        Callback<Pixabay?> {
        override fun onFailure(call: Call<Pixabay?>, t: Throwable) {
            Log.d("hello", "loadInitial:$t")
        }
        override fun onResponse(call: Call<Pixabay?>, response: retrofit2.Response<Pixabay?>) {
            val dataList = response.body()?.hits?.toList()
            callback.onResult(dataList!!,null,2)
        }
    })
    }

    private fun getMap(pager:Int) {
        map["key"] = "18406861-c6e0c4888076e3bb006248fd0"
        map["q"] = keyWords
        map["per_page"] = "100"
        map["page"] = pager.toString()
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
        getMap(params.key)
        RetrofitSingleton.getInstance().getService().getPhoto(map).enqueue(object :
            Callback<Pixabay?> {
            override fun onFailure(call: Call<Pixabay?>, t: Throwable) {
                Log.d("hello", "loadInitial:$t")
            }
            override fun onResponse(call: Call<Pixabay?>, response: retrofit2.Response<Pixabay?>) {
                val dataList = response.body()?.hits?.toList()
                callback.onResult(dataList!!,params.key+1)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
    }
}