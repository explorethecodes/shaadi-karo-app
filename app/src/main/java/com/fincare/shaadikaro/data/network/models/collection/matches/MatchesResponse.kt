package com.fincare.shaadikaro.data.network.models.collection.matches
import com.fincare.shaadikaro.data.network.models.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class MatchesResponse(
    @SerializedName("results") var results: List<Person>? = null,
) : BaseResponse()

data class Person(
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("name") var name: Name? = null,
    @SerializedName("location") var location: Location? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("login") var login: Login? = null,
    @SerializedName("dob") var dob: Dob? = null,
    @SerializedName("registered") var registered: Registered? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("cell") var cell: String? = null,
    @SerializedName("id") var id: Id? = null,
    @SerializedName("picture") var picture: Picture? = null,
    @SerializedName("nat") var nat: String? = null
)

data class Name(
    @SerializedName("title") var title: String? = null,
    @SerializedName("first") var first: String? = null,
    @SerializedName("last") var last: String? = null
)

data class Location(
    @SerializedName("street") var street: Street? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("postcode") var postcode: String? = null,
    @SerializedName("coordinates") var coordinates: Coordinates? = null,
    @SerializedName("timezone") var timezone: Timezone? = null
)

data class Login(
    @SerializedName("uuid") var uuid: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("salt") var salt: String? = null,
    @SerializedName("md5") var md5: String? = null,
    @SerializedName("sha1") var sha1: String? = null,
    @SerializedName("sha256") var sha256: String? = null
)

data class Dob(
    @SerializedName("date") var date: String? = null,
    @SerializedName("age") var age: String? = null
)

data class Registered(
    @SerializedName("date") var date: String? = null,
    @SerializedName("age") var age: String? = null
)

data class Id(
    @SerializedName("name") var name: String? = null,
    @SerializedName("value") var value: String? = null
)

data class Picture(
    @SerializedName("large") var large: String? = null,
    @SerializedName("medium") var medium: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null
)

data class Street(
    @SerializedName("number") var number: String? = null,
    @SerializedName("name") var name: String? = null
)

data class Coordinates(
    @SerializedName("latitude") var latitude: String? = null,
    @SerializedName("longitude") var longitude: String? = null
)

data class Timezone(
    @SerializedName("offset") var offset: String? = null,
    @SerializedName("description") var description: String? = null
)