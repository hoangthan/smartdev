package com.smartdev.libraries.movie.data.network

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.smartdev.libraries.movie.data.network.movie.MovieApiService
import com.smartdev.libraries.resource.NetworkResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMovieApiService(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        eitherCallAdapterFactory: EitherCallAdapterFactory,
    ): MovieApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(eitherCallAdapterFactory)
            .addConverterFactory(converterFactory)
            .baseUrl(NetworkResource.BASE_URL)
            .build()
            .create(MovieApiService::class.java)
    }
}
