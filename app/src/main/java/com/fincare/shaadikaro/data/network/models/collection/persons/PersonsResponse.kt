package com.fincare.shaadikaro.data.network.models.collection.persons
import com.fincare.shaadikaro.data.local.database.entities.Person
import com.fincare.shaadikaro.data.network.models.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class PersonsResponse(
    @SerializedName("results") var results: List<Person>? = null,
) : BaseResponse()