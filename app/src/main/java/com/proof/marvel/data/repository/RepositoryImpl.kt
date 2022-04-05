package com.proof.marvel.data.repository

import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.data.repository.dataSource.RemoteDataSource
import com.proof.marvel.domain.repository.Repository
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override fun getList(offset: Int): Observable<MarvelApiResponse> {

        return remoteDataSource.getList(offset)
    }

    override suspend fun getDetails(characterId: Int): Resource<MarvelApiResponse> {
        return responseToResource(
            remoteDataSource.getDetails(characterId)
        )
    }

    private fun responseToResource(response: Response<MarvelApiResponse>): Resource<MarvelApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}