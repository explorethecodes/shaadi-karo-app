package com.fincare.shaadikaro.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fincare.shaadikaro.data.local.database.AppDatabase
import com.fincare.shaadikaro.data.local.database.entities.Suggestion
import com.fincare.shaadikaro.data.local.preference.AppPreference
import com.fincare.shaadikaro.data.network.Api
import com.fincare.shaadikaro.data.network.models.collection.suggestions.SuggestionsRequest
import com.fincare.shaadikaro.data.network.utils.*
import com.fincare.shaadikaro.ui.home.suggestions.SuggestionAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalDateTime
import javax.inject.Inject

private val MINIMUM_INTERVAL = 6

class HomeRepository @Inject constructor(private val api: Api, private val appPreference: AppPreference, private val appDatabase: AppDatabase) : NetworkCall() {

    private val suggestions = MutableLiveData<List<Suggestion>>()

    init {
        suggestions.observeForever {
            insertSuggestions(it)
        }
    }

    private fun insertSuggestions(suggestions: List<Suggestion>) {
        CoroutineScope(Dispatchers.IO).launch {
            appPreference.setLastSavedAt(LocalDateTime.now().toString())
            appDatabase.getSuggestionsDao().insertSuggestions(suggestions)
        }
    }

    fun updateSuggestion(suggestion: Suggestion, action: SuggestionAction) {
        CoroutineScope(Dispatchers.IO).launch {
            when(action){
                SuggestionAction.ACCEPT -> appDatabase.getSuggestionsDao().updateSuggestion(suggestion)
                SuggestionAction.DECLINE -> appDatabase.getSuggestionsDao().updateSuggestion(suggestion)
            }
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return true //ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    fun requestSuggestions(request: SuggestionsRequest, networkCallListener: NetworkCallListener?, callback: (List<Suggestion>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val lastSavedAt = appPreference.getLastSavedAt()

            if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
                try {
                    networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                    val suggestionsResponse = apiRequest { api.suggestions(
                        request.results.toString()
                    )}

                    suggestionsResponse.results?.let {
                        suggestions.postValue(it)
                    }

                    networkCallListener?.onNetworkCallSuccess(CallInfo(callCode = request.callCode))

                } catch (e: ApiException) {
                    networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
                } catch (e: NoInternetException) {
                    networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
                } catch (e : Exception){
                    networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
                }
            } else {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode))
            }

            getSuggestions().observeForever {
                callback(it.reversed())
            }
        }
    }

    private suspend fun getSuggestions() : LiveData<List<Suggestion>>{
        return withContext(Dispatchers.IO){
            appDatabase.getSuggestionsDao().getSuggestions()
        }
    }
}