package com.proof.marvel.domain.usecase

import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.domain.repository.Repository

class GetDetailsUseCase(private val repository: Repository) {
    suspend fun execute(characterId: Int): Resource<MarvelApiResponse> {
        return repository.getDetails(characterId)
    }
}