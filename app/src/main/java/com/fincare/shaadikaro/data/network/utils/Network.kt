package com.fincare.shaadikaro.data.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.IOException

object Network{
    const val BASE_URL = "https://randomuser.me"
}

@RequiresApi(Build.VERSION_CODES.M)
fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.let {
        it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    }
    return result
}

interface NetworkCallListener {
    fun onNetworkCallStarted(callInfo: CallInfo)
    fun onNetworkCallSuccess(callInfo: CallInfo)
    fun onNetworkCallFailure(callInfo: CallInfo)
    fun onNetworkCallCancel(callInfo: CallInfo)
}

class ApiException(message: String) : IOException(message)

class NoInternetException(message: String) : IOException(message)

enum class CallCode {
    MATCHES
}

data class CallInfo(
    var callCode: CallCode?=null,
    var callStatus: CallStatus?=null,
    var exception: Exception?=null
)

data class CallSuccess(var message: String) : CallStatus()
data class CallFailed(var message: String) : CallStatus()

open class CallStatus