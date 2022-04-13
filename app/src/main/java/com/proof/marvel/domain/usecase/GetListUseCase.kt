package com.proof.marvel.domain.usecase

import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.domain.repository.Repository
import io.reactivex.Observable

class GetListUseCase(private val repository: Repository) {
    suspend fun execute(offset: Int): Resource<MarvelApiResponse>{
        return repository.getList(offset)
    }
}