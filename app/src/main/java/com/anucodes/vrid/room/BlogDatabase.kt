package com.anucodes.vrid.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anucodes.vrid.room.model.OffBlogs

@Database(
    entities = [OffBlogs::class],
    version = 1
)
abstract class BlogDatabase:RoomDatabase() {
    abstract fun blogOffline(): BlogDao
}