package com.anucodes.vrid.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.anucodes.vrid.room.model.OffBlogs
import kotlinx.coroutines.flow.Flow


@Dao
interface BlogDao {

    @Upsert
    suspend fun addBlog(blogs: List<OffBlogs>)

    @Query("SELECT * FROM offblogs")
    fun  getAllWords(): Flow<List<OffBlogs>>
}