package com.proof.marvel.data.repository.dataSourceImpl

import com.proof.marvel.data.api.ApiService
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.data.repository.dataSource.RemoteDataSource
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {
    override suspend fun getList(offset: Int): Response<MarvelApiResponse> {
        return apiService.getCharactersList(offset)
    }

    override suspend fun getDetails(characterId: Int): Response<MarvelApiResponse> {
        return apiService.getCharacterInfo(characterId)
    }
}