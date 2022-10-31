package com.fincare.shaadikaro.data.network.api.collection.matches

import com.fincare.shaadikaro.data.network.api.base.BaseData

class MatchesData(var matchesResponse: MatchesResponse) : BaseData(matchesResponse){

    fun getMatches() : List<Person>? {
        return matchesResponse.results
    }
}