package com.proof.marvel.presentation.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.proof.marvel.BuildConfig
import com.proof.marvel.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    const val HASH = "hash"
    const val APIKEY = "apikey"
    const val TS = "ts"
    const val TS_VALUE = "1"

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val defaultRequest = chain.request()
        val hashSignature = "$TS_VALUE${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}".md5()
        val defaultHttpUrl = defaultRequest.url
        val httpUrl = defaultHttpUrl.newBuilder()
            .addQueryParameter(TS, TS_VALUE)
            .addQueryParameter(APIKEY, BuildConfig.PUBLIC_API_KEY)
            .addQueryParameter(HASH, hashSignature)
            .build()

        val requestBuilder = defaultRequest.newBuilder().url(httpUrl)

        chain.proceed(requestBuilder.build())
    }

    private val apiClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
        addInterceptor { chain ->
            val defaultRequest = chain.request()
            val hashSignature = "$TS_VALUE${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}".md5()
            val defaultHttpUrl = defaultRequest.url
            val httpUrl = defaultHttpUrl.newBuilder()
                .addQueryParameter(TS, TS_VALUE)
                .addQueryParameter(APIKEY, BuildConfig.PUBLIC_API_KEY)
                .addQueryParameter(HASH, hashSignature)
                .build()

            val requestBuilder = defaultRequest.newBuilder().url(httpUrl)

            chain.proceed(requestBuilder.build())
        }
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(apiClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.URL_BASE)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}