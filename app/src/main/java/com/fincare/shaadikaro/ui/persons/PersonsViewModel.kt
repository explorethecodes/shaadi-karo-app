package com.fincare.shaadikaro.ui.persons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fincare.shaadikaro.data.local.database.entities.Person
import com.fincare.shaadikaro.data.network.utils.NetworkCallListener
import com.fincare.shaadikaro.data.network.models.collection.persons.PersonsData
import com.fincare.shaadikaro.data.network.models.collection.persons.PersonsRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    //------------------------------------------- NETWORK ------------------------------------------//
    var networkCallListener : NetworkCallListener? =null

    //------------------------------------------- SUGGESTIONS -------------------------------------------//
    var personsRequest = PersonsRequest()
    val persons: LiveData<List<Person>> get() = _persons
    private var _persons = MutableLiveData<List<Person>>()
    fun requestPersons(personsRequest: PersonsRequest) {
        repository.requestPersons(personsRequest,networkCallListener) {
            _persons.value = it
        }
    }

    fun updatePerson(person: Person){
        repository.updatePerson(person)
    }

    fun setIsFetchNeeded(isNeeded : Boolean) {
        repository.setIsFetchNeeded(isNeeded)
    }
}