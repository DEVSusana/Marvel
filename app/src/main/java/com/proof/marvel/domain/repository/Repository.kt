package com.proof.marvel.domain.repository

import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import retrofit2.Response

interface Repository {
    suspend fun getList(): Resource<MarvelApiResponse>
    suspend fun getDetails(characterId: Int): Resource<MarvelApiResponse>
}