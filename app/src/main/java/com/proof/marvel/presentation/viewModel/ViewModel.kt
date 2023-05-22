package com.proof.marvel.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.data.model.Result
import com.proof.marvel.domain.repository.Repository
import com.proof.marvel.domain.usecase.GetDetailsUseCase
import com.proof.marvel.domain.usecase.GetListUseCase
import com.proof.marvel.view.pagin.ResultDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModel(
    private val app: Application,
    private val getListUseCase: GetListUseCase,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val repository: Repository
) : AndroidViewModel(app) {

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

    val resultCharacter: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 50)) {
        ResultDataSource()
    }.flow.cachedIn(viewModelScope)

    private val _getDetail: MutableLiveData<Resource<MarvelApiResponse>> by lazy {
        MutableLiveData<Resource<MarvelApiResponse>>()
    }
    val getDetail : LiveData<Resource<MarvelApiResponse>> get() = _getDetail

    fun getDetailResponse(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _getDetail.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getDetailsUseCase.execute(id)
                _getDetail.postValue(apiResult)
            } else {
                _getDetail.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            _getDetail.postValue(Resource.Error(e.message.toString()))
        }
    }


}