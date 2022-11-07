package com.fincare.shaadikaro.di

import android.content.Context
import com.fincare.shaadikaro.data.local.database.AppDatabase
import com.fincare.shaadikaro.data.local.database.daos.SuggestionsDao
import com.fincare.shaadikaro.data.network.utils.NetworkInterceptor
import com.fincare.shaadikaro.data.network.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.invoke(appContext)
    }

    @Provides
    fun provideSuggestionsDao(appDatabase: AppDatabase): SuggestionsDao {
        return appDatabase.getSuggestionsDao()
    }

    @Provides
    fun provideApi(networkInterceptor: NetworkInterceptor) : Api {
        return Api.invoke(networkInterceptor)
    }
}