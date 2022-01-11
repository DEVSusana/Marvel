package com.proof.marvel.domain.usecase

import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.domain.repository.Repository

class GetListUseCase(private val repository: Repository) {
    suspend fun execute(): Resource<MarvelApiResponse> {
        return repository.getList()
    }
}