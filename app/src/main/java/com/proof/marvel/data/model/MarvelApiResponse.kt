package com.proof.marvel.data.model

data class MarvelApiResponse(
    val code: Int,
    val `data`: Data,
    val etag: String
)