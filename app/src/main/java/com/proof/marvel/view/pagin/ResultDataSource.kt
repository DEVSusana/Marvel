package com.proof.marvel.view.pagin


import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.proof.marvel.data.api.ApiService
import com.proof.marvel.data.model.Result
import com.proof.marvel.domain.repository.Repository
import io.reactivex.disposables.CompositeDisposable

class ResultDataSource (
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Result>()
        {
    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Int>, callback: PageKeyedDataSource.LoadInitialCallback<Int, Result>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Result>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Result>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(
        requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        initialCallback: PageKeyedDataSource.LoadInitialCallback<Int, Result>?,
        callback: PageKeyedDataSource.LoadCallback<Int, Result>?
    ) {
        compositeDisposable.add(
            repository.getList(requestedPage * requestedLoadSize)
                .subscribe(
                    { response ->
                        Log.d("", "Loading page: $requestedPage")
                        initialCallback?.onResult(response.data.results, null, adjacentPage)
                        callback?.onResult(response.data.results, adjacentPage)
                    },
                    { error ->
                        Log.e("", "error", error)
                    }
                )
        )
    }
}