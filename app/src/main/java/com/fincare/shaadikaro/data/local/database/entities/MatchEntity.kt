package com.fincare.shaadikaro.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val ROOM_ID = 0

@Entity
data class MatchEntity(
        var id: Int? = null,
        var type: String? = null,
        var userName: String? = null
){
    @PrimaryKey()
    var roomId: Int = ROOM_ID
}