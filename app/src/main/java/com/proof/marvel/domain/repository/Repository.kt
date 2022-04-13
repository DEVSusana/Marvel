package com.proof.marvel.domain.repository

import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import io.reactivex.Observable
import retrofit2.Response

interface Repository {
    suspend fun getList(offset: Int): Resource<MarvelApiResponse>
    suspend fun getDetails(characterId: Int): Resource<MarvelApiResponse>
}