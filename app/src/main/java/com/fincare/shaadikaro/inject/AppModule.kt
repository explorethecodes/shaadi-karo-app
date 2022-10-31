package com.fincare.shaadikaro.inject

import com.fincare.shaadikaro.data.network.NetworkInterceptor
import com.fincare.shaadikaro.data.network.api.Api
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