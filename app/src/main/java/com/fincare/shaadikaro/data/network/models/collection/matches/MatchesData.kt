package com.fincare.shaadikaro.data.network.models.collection.matches

import com.fincare.shaadikaro.data.network.models.base.BaseData

class MatchesData(var matchesResponse: MatchesResponse) : BaseData(matchesResponse){

    fun getMatches() : List<Person>? {
        return matchesResponse.results
    }
}