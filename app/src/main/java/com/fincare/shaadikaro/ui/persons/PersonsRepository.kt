package com.fincare.shaadikaro.ui.persons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fincare.shaadikaro.data.local.database.AppDatabase
import com.fincare.shaadikaro.data.local.database.entities.Person
import com.fincare.shaadikaro.data.local.preference.AppPreference
import com.fincare.shaadikaro.data.network.Api
import com.fincare.shaadikaro.data.network.models.collection.persons.PersonsRequest
import com.fincare.shaadikaro.data.network.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//private val MINIMUM_INTERVAL = 6

class HomeRepository @Inject constructor(private val api: Api, private val appPreference: AppPreference, private val appDatabase: AppDatabase) : NetworkCall() {

    private val persons = MutableLiveData<List<Person>>()

    init {
        persons.observeForever {
            insertPersons(it)
        }
    }

    private fun insertPersons(persons: List<Person>) {
        CoroutineScope(Dispatchers.IO).launch {
//            appPreference.setLastSavedAt(LocalDateTime.now().toString())
            appDatabase.getPersonsDao().insertPersons(persons)
        }
    }

    fun updatePerson(person: Person) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.getPersonsDao().updatePerson(person)
        }
    }

//    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
//        val lastSavedAt = appPreference.getLastSavedAt()
//
//        lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))
//
//        return appPreference.isFetchNeeded()
//    }

    fun requestPersons(request: PersonsRequest, networkCallListener: NetworkCallListener?, callback: (List<Person>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            if (isFetchNeeded()) {
                try {
                    networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                    val personsResponse = apiRequest { api.persons(
                        request.results.toString()
                    )}

                    personsResponse.results?.let {
                        persons.postValue(it)
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

            getPersons().observeForever {
                callback(it.reversed())
            }
        }
    }

    private suspend fun getPersons() : LiveData<List<Person>>{
        return withContext(Dispatchers.IO){
            appDatabase.getPersonsDao().getPersons()
        }
    }

    private fun isFetchNeeded(): Boolean {
        return appPreference.isFetchNeeded()
    }

    fun setIsFetchNeeded(isNeeded: Boolean) {
        appPreference.setIsFetchNeeded(isNeeded)
    }
}