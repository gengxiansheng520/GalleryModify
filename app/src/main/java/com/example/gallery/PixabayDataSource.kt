package com.example.gallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

enum class NetworkStatus {
    INITIAL_LOADING,
    LOADING,
    LOADED,
    FAILED,
    COMPLETED
}
class PixabayDataSource:PageKeyedDataSource<Int, PhotoItem>() {
    var retry : (()->Any)? = null
    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus> = _networkStatus
    private val keyWords = arrayOf(
        "cat",
        "dog",
        "car",
        "beauty",
        "phone",
        "computer",
        "flower",
        "animal"
    ).random()
    private var map = HashMap<String, String>()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoItem>
    ) {
        getMap(1)
        retry = null
        _networkStatus.postValue(NetworkStatus.INITIAL_LOADING)
        RetrofitSingleton.getInstance().getService().getPhoto(map)?.enqueue(object :
            Callback<Pixabay?> {
            override fun onFailure(call: Call<Pixabay?>, t: Throwable) {
                retry = { loadInitial(params, callback) }
                _networkStatus.postValue(NetworkStatus.FAILED)
                Log.d("hello", "loadInitial:$t")
            }

            override fun onResponse(call: Call<Pixabay?>, response: retrofit2.Response<Pixabay?>) {

                _networkStatus.postValue(NetworkStatus.LOADED)
                val dataList = response.body()?.hits?.toList()
                callback.onResult(dataList!!, null, 2)
            }
        })
    }

    private fun getMap(pager: Int) {
        map["key"] = "18406861-c6e0c4888076e3bb006248fd0"
        map["q"] = keyWords
        map["per_page"] = "100"
        map["page"] = pager.toString()
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
        //getMap(params.key)
        getMap(7)
        retry = null
        _networkStatus.postValue(NetworkStatus.LOADING)
        RetrofitSingleton.getInstance().getService().getPhoto(map)?.enqueue(object :
            Callback<Pixabay?> {
            override fun onFailure(call: Call<Pixabay?>, t: Throwable) {
                if (t.toString() == "java.net.SocketTimeoutException: 111timeout") {
                    _networkStatus.postValue(NetworkStatus.COMPLETED)
                } else {
                    retry = { loadAfter(params, callback) }
                    _networkStatus.postValue(NetworkStatus.FAILED)
                }
                Log.d("hello", "loadAfter:$t")
            }

            override fun onResponse(call: Call<Pixabay?>, response: retrofit2.Response<Pixabay?>) {
                _networkStatus.postValue(NetworkStatus.LOADED)
                Log.d("hello", "loadAfter:" + response.body().toString())
                val res = response.body() ?: return
                val dataList = res.hits.toList()
                callback.onResult(dataList, params.key + 1)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
    }
}