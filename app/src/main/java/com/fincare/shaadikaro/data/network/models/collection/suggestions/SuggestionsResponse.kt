package com.fincare.shaadikaro.data.network.models.collection.suggestions
import com.fincare.shaadikaro.data.local.database.entities.Suggestion
import com.fincare.shaadikaro.data.network.models.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class SuggestionsResponse(
    @SerializedName("results") var results: List<Suggestion>? = null,
) : BaseResponse()