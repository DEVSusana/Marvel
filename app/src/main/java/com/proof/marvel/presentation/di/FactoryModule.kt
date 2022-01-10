package com.proof.marvel.presentation.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.proof.marvel.domain.usecase.GetDetailsUseCase
import com.proof.marvel.domain.usecase.GetListUseCase
import com.proof.marvel.presentation.viewModel.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactoryModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(
        application: Application,
        getListUseCase: GetListUseCase,
        getDetailsUseCase: GetDetailsUseCase
    ): ViewModelProvider.Factory {
        return ViewModelFactory(
            application,
            getListUseCase,
            getDetailsUseCase
        )
    }

}