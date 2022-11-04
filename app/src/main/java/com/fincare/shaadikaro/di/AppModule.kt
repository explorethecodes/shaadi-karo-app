package com.fincare.shaadikaro.di

import com.fincare.shaadikaro.data.network.utils.NetworkInterceptor
import com.fincare.shaadikaro.data.network.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApi(networkInterceptor: NetworkInterceptor) : Api {
        return Api.invoke(networkInterceptor)
    }
}