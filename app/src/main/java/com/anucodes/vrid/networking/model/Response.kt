package com.anucodes.vrid.networking.model

import com.anucodes.vrid.room.model.OffBlogs

data class Response(
    val id: Int,
    val type: String,
    val status: String,
    val date: String,
    val title: Text,
    val content: Text,
    val jetpack_featured_media_url: String
)

data class Text(
    val rendered: String
)

fun OffBlogs.toResponse(): Response {
    return Response(
        id = this.id,
        type = this.type,
        status = this.status,
        date = this.date,
        title = Text(rendered = this.title),
        content = Text(rendered = this.content),
        jetpack_featured_media_url = this.jetpack_featured_media_url
    )
}
