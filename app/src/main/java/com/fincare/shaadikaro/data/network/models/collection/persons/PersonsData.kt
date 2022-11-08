package com.fincare.shaadikaro.data.network.models.collection.persons

import com.fincare.shaadikaro.data.local.database.entities.Person
import com.fincare.shaadikaro.data.network.models.base.BaseData

class PersonsData(var matchesResponse: PersonsResponse) : BaseData(matchesResponse){

    fun getMatches() : List<Person>? {
        return matchesResponse.results
    }
}