package com.fincare.shaadikaro.data.local.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fincare.shaadikaro.data.local.database.entities.Suggestion

@Dao
interface SuggestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSuggestions(suggestions : List<Suggestion>)

    @Update
    fun updateSuggestion(suggestion: Suggestion)

    @Transaction
    @Query("SELECT * FROM Suggestions")
    fun getSuggestions() : LiveData<List<Suggestion>>
}