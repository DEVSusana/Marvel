package com.proof.marvel.view.pagin


import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.proof.marvel.data.api.ApiService
import com.proof.marvel.data.model.Result
import com.proof.marvel.domain.repository.Repository
import com.proof.marvel.presentation.di.NetModule
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.io.IOException

class ResultDataSource: PagingSource<Int, Result>(){

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage = params.key ?: 1
            val marvelList = NetModule.provideApiService(NetModule.provideRetrofit()).getCharactersList(nextPage)

            LoadResult.Page(
                data = marvelList.body()?.data!!.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (marvelList.body()?.data?.results.isNullOrEmpty()) null else marvelList.body()?.data?.offset?.plus(
                    50
                )
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}