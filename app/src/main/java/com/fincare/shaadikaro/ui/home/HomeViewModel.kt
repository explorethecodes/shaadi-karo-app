package com.fincare.shaadikaro.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fincare.shaadikaro.data.local.database.entities.Suggestion
import com.fincare.shaadikaro.data.network.utils.NetworkCallListener
import com.fincare.shaadikaro.data.network.models.collection.suggestions.SuggestionsData
import com.fincare.shaadikaro.data.network.models.collection.suggestions.SuggestionsRequest
import com.fincare.shaadikaro.ui.home.suggestions.SuggestionAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    //------------------------------------------- NETWORK ------------------------------------------//
    var networkCallListener : NetworkCallListener? =null

    //------------------------------------------- MATCHES -------------------------------------------//
    var suggestionsData : SuggestionsData? = null
    var suggestionsRequest = SuggestionsRequest()
    val suggestions: LiveData<List<Suggestion>> get() = _suggestions
    private var _suggestions = MutableLiveData<List<Suggestion>>()
    fun requestSuggestions(suggestionsRequest: SuggestionsRequest) {
        repository.requestSuggestions(suggestionsRequest,networkCallListener) {
            _suggestions.value = it
        }
    }

    fun updateSuggestion(suggestion: Suggestion, action: SuggestionAction){
        repository.updateSuggestion(suggestion,action)
    }
}