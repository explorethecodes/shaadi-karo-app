package com.fincare.shaadikaro.data.network.models.collection.suggestions

import com.fincare.shaadikaro.data.local.database.entities.Suggestion
import com.fincare.shaadikaro.data.network.models.base.BaseData

class SuggestionsData(var matchesResponse: SuggestionsResponse) : BaseData(matchesResponse){

    fun getMatches() : List<Suggestion>? {
        return matchesResponse.results
    }
}