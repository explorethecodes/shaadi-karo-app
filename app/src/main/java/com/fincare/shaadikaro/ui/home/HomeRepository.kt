package com.fincare.shaadikaro.ui.home

import com.fincare.shaadikaro.data.local.preference.PreferenceProvider
import com.fincare.shaadikaro.data.network.*
import com.fincare.shaadikaro.data.network.api.Api
import com.fincare.shaadikaro.data.network.api.collection.matches.MatchesRequest
import com.fincare.shaadikaro.data.network.api.collection.matches.MatchesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: Api, private val preferenceProvider: PreferenceProvider) : NetworkCall() {
    
    fun requestMatches(request: MatchesRequest, networkCallListener: NetworkCallListener?, callback : (MatchesResponse?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))
                
                val matchesResponse = apiRequest { api.matches(
                    request.results.toString()
                )}
                callback(matchesResponse)

                networkCallListener?.onNetworkCallSuccess(CallInfo(callCode = request.callCode))
                
            } catch (e: ApiException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e: NoInternetException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e : Exception){
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            }
        }
    }
}