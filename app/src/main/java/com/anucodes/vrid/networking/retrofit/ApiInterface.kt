package com.anucodes.vrid.networking.retrofit

import com.anucodes.vrid.networking.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("posts")
    suspend fun getAllBlog(
        @Query("per_page") limit: Int,
        @Query("page") page: Int
    ):List<Response>

    @GET("posts/{id}")
    suspend fun getBlogById(
        @Path("id") id: Int
    ): Response
}