package com.proof.marvel.presentation.di

import com.proof.marvel.domain.repository.Repository
import com.proof.marvel.domain.usecase.GetDetailsUseCase
import com.proof.marvel.domain.usecase.GetListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetListUseCase(
        repository: Repository
    ): GetListUseCase {
        return GetListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDetailsUseCase(
        repository: Repository
    ): GetDetailsUseCase{
        return GetDetailsUseCase(repository)
    }

}