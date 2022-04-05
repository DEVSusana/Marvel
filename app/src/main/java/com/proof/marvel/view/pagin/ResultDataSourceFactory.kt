package com.proof.marvel.view.pagin


import androidx.paging.DataSource
import com.proof.marvel.data.api.ApiService
import com.proof.marvel.data.model.Result
import com.proof.marvel.domain.repository.Repository
import io.reactivex.disposables.CompositeDisposable

class ResultDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val repository: Repository
) : DataSource.Factory<Int, Result>() {

    override fun create(): DataSource<Int, Result> {
        return ResultDataSource(repository, compositeDisposable)
    }
}