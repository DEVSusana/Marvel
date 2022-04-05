package com.proof.marvel.domain.repository

import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import io.reactivex.Observable
import retrofit2.Response

interface Repository {
    fun getList(offset: Int): Observable<MarvelApiResponse>
    suspend fun getDetails(characterId: Int): Resource<MarvelApiResponse>
}