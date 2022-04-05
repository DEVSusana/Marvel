package com.proof.marvel.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.api.ApiService
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.data.model.Result
import com.proof.marvel.domain.repository.Repository
import com.proof.marvel.domain.usecase.GetDetailsUseCase
import com.proof.marvel.domain.usecase.GetListUseCase
import com.proof.marvel.view.pagin.ResultDataSourceFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ViewModel(
    private val app: Application,
    private val getListUseCase: GetListUseCase,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val repository: Repository
) : AndroidViewModel(app) {

    var getList: Observable<PagedList<Result>>

    private val compositeDisposable = CompositeDisposable()

    private val pagedSize = 20

    private val sourceFactory: ResultDataSourceFactory = ResultDataSourceFactory(
        compositeDisposable,
        repository
    )

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(pagedSize)
            .setInitialLoadSizeHint(pagedSize * 3)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        getList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()

    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

//    val _getList: MutableLiveData<Resource<MarvelApiResponse>> = MutableLiveData()
//    val getList get() = _getList
//
//    fun getListResponse() = viewModelScope.launch(Dispatchers.IO) {
//        getList.postValue(Resource.Loading())
//        try {
//            if (isNetworkAvailable(app)) {
//                val apiResult = getListUseCase.execute()
//                getList.postValue(apiResult)
//            } else {
//                getList.postValue(Resource.Error("Internet is not available"))
//            }
//        } catch (e: Exception) {
//            getList.postValue(Resource.Error(e.message.toString()))
//        }
//    }

    val _getDetail: MutableLiveData<Resource<MarvelApiResponse>> = MutableLiveData()
    val getDetail get() = _getDetail

    fun getDetailResponse(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getDetail.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getDetailsUseCase.execute(id)
                getDetail.postValue(apiResult)
            } else {
                getDetail.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            getDetail.postValue(Resource.Error(e.message.toString()))
        }
    }



}