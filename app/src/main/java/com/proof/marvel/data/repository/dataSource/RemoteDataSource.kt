package com.proof.marvel.data.repository.dataSource

import com.proof.marvel.data.model.MarvelApiResponse
import io.reactivex.Observable
import retrofit2.Response

interface RemoteDataSource {
    fun getList(offset: Int): Observable<MarvelApiResponse>
    suspend fun getDetails(characterId: Int): Response<MarvelApiResponse>
}