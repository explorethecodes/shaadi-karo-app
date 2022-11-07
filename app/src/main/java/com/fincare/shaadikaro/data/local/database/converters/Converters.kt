package com.fincare.shaadikaro.data.local.database.converters

import androidx.room.TypeConverter
import com.fincare.shaadikaro.data.local.database.entities.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun nameDataToString(data : Name): String? {
        val type = object : TypeToken<Name>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun nameStringToData(dataString: String?): Name? {
        val type = object : TypeToken<Name>() {}.type
        return Gson().fromJson<Name>(dataString, type)
    }

    @TypeConverter
    fun locationDataToString(data: Location): String? {
        val type = object : TypeToken<Location>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun locationStringToData(dataString: String?): Location? {
        val type = object : TypeToken<Location>() {}.type
        return Gson().fromJson<Location>(dataString, type)
    }

    @TypeConverter
    fun loginDataToString(data: Login): String? {
        val type = object : TypeToken<Login>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun loginStringToData(dataString: String?): Login? {
        val type = object : TypeToken<Login>() {}.type
        return Gson().fromJson<Login>(dataString, type)
    }

    @TypeConverter
    fun dobDataToString(data: Dob): String? {
        val type = object : TypeToken<Dob>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun dobStringToData(dataString: String?): Dob? {
        val type = object : TypeToken<Dob>() {}.type
        return Gson().fromJson<Dob>(dataString, type)
    }

    @TypeConverter
    fun registeredDataToString(data: Registered): String? {
        val type = object : TypeToken<Registered>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun registeredStringToData(dataString: String?): Registered? {
        val type = object : TypeToken<Registered>() {}.type
        return Gson().fromJson<Registered>(dataString, type)
    }

    @TypeConverter
    fun idDataToString(data: Id): String? {
        val type = object : TypeToken<Id>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun idStringToData(dataString: String?): Id? {
        val type = object : TypeToken<Id>() {}.type
        return Gson().fromJson<Id>(dataString, type)
    }

    @TypeConverter
    fun pictureDataToString(data: Picture): String? {
        val type = object : TypeToken<Picture>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun pictureStringToData(dataString: String?): Picture? {
        val type = object : TypeToken<Picture>() {}.type
        return Gson().fromJson<Picture>(dataString, type)
    }

    @TypeConverter
    fun streetDataToString(data: Street): String? {
        val type = object : TypeToken<Street>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun streetStringToData(dataString: String?): Street? {
        val type = object : TypeToken<Street>() {}.type
        return Gson().fromJson<Street>(dataString, type)
    }

    @TypeConverter
    fun coordinatesDataToString(data: Coordinates): String? {
        val type = object : TypeToken<Coordinates>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun coordinatesStringToData(dataString: String?): Coordinates? {
        val type = object : TypeToken<Coordinates>() {}.type
        return Gson().fromJson<Coordinates>(dataString, type)
    }

    @TypeConverter
    fun timezoneDataToString(data: Timezone): String? {
        val type = object : TypeToken<Timezone>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun timezoneStringToData(dataString: String?): Timezone? {
        val type = object : TypeToken<Timezone>() {}.type
        return Gson().fromJson<Timezone>(dataString, type)
    }
}