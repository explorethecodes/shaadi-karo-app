package com.fincare.shaadikaro.data.network.api.base

import com.google.gson.annotations.SerializedName

open class BaseRequest(
    @SerializedName("results") var results : Int = 10
)