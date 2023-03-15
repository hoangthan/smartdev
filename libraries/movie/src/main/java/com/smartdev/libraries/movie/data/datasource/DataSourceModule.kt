package com.smartdev.libraries.movie.data.datasource

import com.smartdev.libraries.movie.data.datasource.movie.MovieDataSource
import com.smartdev.libraries.movie.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindMovieDataSource(impl: MovieDataSource): MovieRepository
}
