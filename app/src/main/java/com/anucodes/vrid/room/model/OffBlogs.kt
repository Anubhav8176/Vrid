package com.anucodes.vrid.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anucodes.vrid.networking.model.Response
import com.anucodes.vrid.networking.model.Text

@Entity
data class OffBlogs(
    @PrimaryKey
    val id: Int,
    val type: String,
    val status: String,
    val date: String,
    val title: String,
    val content: String,
    val jetpack_featured_media_url: String
)

fun Response.toOffBlogs(): OffBlogs{
    return OffBlogs(
        id = this.id,
        type = this.type,
        status = this.status,
        date = this.date,
        title = this.title.rendered,
        content = this.content.rendered,
        jetpack_featured_media_url = this.jetpack_featured_media_url
    )
}
