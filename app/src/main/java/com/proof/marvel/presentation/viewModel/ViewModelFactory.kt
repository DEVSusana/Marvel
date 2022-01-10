package com.proof.marvel.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proof.marvel.domain.usecase.GetDetailsUseCase
import com.proof.marvel.domain.usecase.GetListUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val app: Application,
    private val getListUseCase: GetListUseCase,
    private val getDetailsUseCase: GetDetailsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            Application::class.java,
            GetListUseCase::class.java,
            GetDetailsUseCase::class.java
        )
            .newInstance(
                app,
                getListUseCase,
                getDetailsUseCase
            )
    }
}