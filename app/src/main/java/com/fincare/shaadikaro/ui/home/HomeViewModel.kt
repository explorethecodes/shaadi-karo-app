package com.fincare.shaadikaro.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fincare.shaadikaro.data.network.NetworkCallListener
import com.fincare.shaadikaro.data.network.api.collection.matches.MatchesData
import com.fincare.shaadikaro.data.network.api.collection.matches.MatchesRequest
import com.fincare.shaadikaro.data.network.api.collection.matches.MatchesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    //------------------------------------------- NETWORK ------------------------------------------//
    var networkCallListener : NetworkCallListener? =null

    //------------------------------------------- MATCHES -------------------------------------------//
    var matchesData : MatchesData? = null
    var matchesRequest = MatchesRequest()
    val matchesResponse: LiveData<MatchesResponse> get() = _matchesResponse
    private val _matchesResponse = MutableLiveData<MatchesResponse>()
    fun requestMatches(matchesRequest: MatchesRequest) {
        if (validateMatches()) {
            repository.requestMatches(matchesRequest,networkCallListener){
                _matchesResponse.value = it
            }
        }
    }
    private fun validateMatches(): Boolean {
        return true
    }
}