package com.fincare.shaadikaro.data.network

import com.fincare.shaadikaro.data.network.utils.Network.BASE_URL
import com.fincare.shaadikaro.data.network.models.collection.matches.MatchesResponse
import com.fincare.shaadikaro.data.network.utils.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface Api {

    //-------------------------------------- MOVIES -----------------------------------------//
    @GET("/api/")
    suspend fun matches(
        @Query("results",encoded = true) results : String,
    ) : Response<MatchesResponse>

    companion object{
        operator fun invoke(
            networkInterceptor: NetworkInterceptor
        ) : Api {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okkHttpclient = OkHttpClient.Builder()
                    .readTimeout(10000, TimeUnit.SECONDS)
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .addInterceptor(networkInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}