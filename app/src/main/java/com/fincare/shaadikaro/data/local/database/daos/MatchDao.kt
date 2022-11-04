package com.fincare.shaadikaro.data.local.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fincare.shaadikaro.data.local.database.entities.MatchEntity
import com.fincare.shaadikaro.data.network.models.collection.matches.Person
import com.fincare.shaadikaro.data.local.database.entities.ROOM_ID


@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMatch(matchEntity: MatchEntity)

    @Transaction
    @Query("SELECT * FROM MatchEntity WHERE roomId = $ROOM_ID")
    fun getMatches() : LiveData<List<MatchEntity>>
}