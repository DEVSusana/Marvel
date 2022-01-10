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
import com.proof.marvel.data.Utils.Resource
import com.proof.marvel.data.model.MarvelApiResponse
import com.proof.marvel.domain.usecase.GetDetailsUseCase
import com.proof.marvel.domain.usecase.GetListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModel(
    private val app: Application,
    private val getListUseCase: GetListUseCase,
    private val getDetailsUseCase: GetDetailsUseCase
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

    val getList: MutableLiveData<Resource<MarvelApiResponse>> = MutableLiveData()

    fun getListResponse() = viewModelScope.launch(Dispatchers.IO) {
        getList.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getListUseCase.execute()
                getList.postValue(apiResult)
            } else {
                getList.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            getList.postValue(Resource.Error(e.message.toString()))
        }
    }

    val getDetail: MutableLiveData<Resource<MarvelApiResponse>> = MutableLiveData()
    val getDetailState = mutableStateOf(getDetail.value)

    fun getDetailResponse() = viewModelScope.launch(Dispatchers.IO) {
        getDetail.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getListUseCase.execute()
                getDetailState.value = apiResult
                getDetail.postValue(apiResult)
            } else {
                getDetail.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            getDetail.postValue(Resource.Error(e.message.toString()))
        }
    }


}