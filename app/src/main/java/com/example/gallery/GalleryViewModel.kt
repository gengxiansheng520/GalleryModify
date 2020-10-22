package com.example.gallery

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import java.util.*

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val factory = PixabayDataSourceFactory()
    val networkStatus: LiveData<NetworkStatus> = Transformations.switchMap(factory.pixabayDataSource) { it.networkStatus }
    val pagedListLiveData = factory.toLiveData(1)
    fun resetQuery() {
        pagedListLiveData.value?.dataSource?.invalidate()
    }
    fun retry() {
        factory.pixabayDataSource.value?.retry?.invoke()
    }
}