package com.fincare.shaadikaro.ui.home

import com.fincare.shaadikaro.data.local.preference.AppPreference
import com.fincare.shaadikaro.data.network.Api
import com.fincare.shaadikaro.data.network.models.collection.matches.MatchesRequest
import com.fincare.shaadikaro.data.network.models.collection.matches.MatchesResponse
import com.fincare.shaadikaro.data.network.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: Api, private val preferenceProvider: AppPreference) : NetworkCall() {
    
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