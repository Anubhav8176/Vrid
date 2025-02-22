package com.anucodes.vrid.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anucodes.vrid.networking.model.Response
import com.anucodes.vrid.networking.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {

    private val _allBlogs = MutableStateFlow<List<Response>>(emptyList())
    val allBlogs = _allBlogs.asStateFlow()

    private val _blog = MutableStateFlow<Response?>(null)
    val blog = _blog.asStateFlow()

    private var pageNum = 1

    init {
        getAllBlogs()
    }

    fun getAllBlogs(){
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.apiInterface.getAllBlog(limit = 15, page =  pageNum)

                _allBlogs.value += response

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
}