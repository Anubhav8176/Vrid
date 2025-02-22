package com.anucodes.vrid.networking.model

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
