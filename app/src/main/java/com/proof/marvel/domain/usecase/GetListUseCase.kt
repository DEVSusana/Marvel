package com.proof.marvel.domain.usecase

import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.domain.repository.Repository
import io.reactivex.Observable

class GetListUseCase(private val repository: Repository) {
    fun execute(offset: Int) : Observable<MarvelApiResponse>{
        return repository.getList(offset)
    }
}