package com.fincare.shaadikaro.data.network.api.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponse(
    @SerializedName("info") var info: Info? = null
): Serializable

data class Info(
    @SerializedName("seed") var seed: String? = null,
    @SerializedName("results") var results: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("version") var version: String? = null
)