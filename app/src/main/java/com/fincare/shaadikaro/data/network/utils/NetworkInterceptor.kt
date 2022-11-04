package com.fincare.shaadikaro.data.network.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    @ApplicationContext context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable(applicationContext))
            throw NoInternetException("Make sure you have an active data connection !")

        var request = chain.request()
        request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()

        return chain.proceed(request)
    }
}