package com.anucodes.vrid.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.anucodes.vrid.networking.model.Response
import com.anucodes.vrid.networking.model.toResponse
import com.anucodes.vrid.networking.retrofit.RetrofitInstance
import com.anucodes.vrid.room.BlogDatabase
import com.anucodes.vrid.room.model.OffBlogs
import com.anucodes.vrid.room.model.toOffBlogs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    application: Application
): AndroidViewModel(application) {

    private val offlineBlogs = Room.databaseBuilder(
        application,
        BlogDatabase::class.java,
        "offBlogs.db"
    ).build()
    private val offlinDao = offlineBlogs.blogOffline()

    private val _allBlogs = MutableStateFlow<List<Response>>(emptyList())
    val allBlogs = _allBlogs.asStateFlow()

    private val _blog = MutableStateFlow<Response?>(null)
    val blog = _blog.asStateFlow()



    private var pageNum = 1

    init {
        if (isInternetAvailable(application)){
            getAllBlogs()
        }else{
            getOfflineBlogs()
        }
    }

    fun getAllBlogs(){
        viewModelScope.launch {
            try{
                var offlineBlog: List<OffBlogs> = emptyList()
                val response = RetrofitInstance.apiInterface.getAllBlog(limit = 15, page =  pageNum)

                _allBlogs.value += response

                if(!response.isNullOrEmpty()){
                    response.forEach {
                        offlineBlog += it.toOffBlogs()
                    }
                    offlinDao.addBlog(offlineBlog)
                }

                pageNum++
            }catch (e: Exception){
                Log.e("Loading the data: ", e.message.toString())
            }
        }
    }

    fun getBlog(id: Int){
        viewModelScope.launch {
            try {
                _blog.value = RetrofitInstance.apiInterface.getBlogById(id = id)
            }catch (e: Exception){
                Log.e("Blog Data: ", e.message.toString())
            }
        }
    }

    fun getOfflineBlogs(){
        viewModelScope.launch {
            offlinDao.getAllWords()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList()
                ).collect{
                    var finalBlog: List<Response> = emptyList()
                    it.forEach{item->
                        finalBlog += item.toResponse()
                    }

                    if (finalBlog.isNotEmpty()) {
                        _allBlogs.value = finalBlog
                    }
                }
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}