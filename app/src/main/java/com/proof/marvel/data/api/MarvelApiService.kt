package com.proof.marvel.data.api

import com.proof.marvel.BuildConfig
import com.proof.marvel.data.model.MarvelApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {
    @GET("/v1/public/characters")
    suspend fun getCharactersList(
        @Query("apikey")
        apikey: String = BuildConfig.API_KEY
    ): Response<MarvelApiResponse>

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterInfo(
        @Path("characterId")
        characterId: Int,
        @Query("apikey")
    apikey: String = BuildConfig.API_KEY
    ): Response<MarvelApiResponse>
}