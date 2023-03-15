package com.smartdev.libraries.movie.data.network

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
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): MovieApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .baseUrl(NetworkResource.BASE_URL)
            .build()
            .create(MovieApiService::class.java)
    }
}
