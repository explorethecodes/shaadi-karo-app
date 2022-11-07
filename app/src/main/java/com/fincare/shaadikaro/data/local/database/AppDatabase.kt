package com.fincare.shaadikaro.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fincare.shaadikaro.data.local.database.converters.Converters
import com.fincare.shaadikaro.data.local.database.daos.SuggestionsDao
import com.fincare.shaadikaro.data.local.database.entities.Suggestion

private const val DATABASE_NAME = "AppDatabase.db"

@Database(entities = [Suggestion::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getSuggestionsDao(): SuggestionsDao

    companion object {

        @Volatile //This variable is immediately visible to all the threads
        private var instance: AppDatabase? = null
        private val LOCK = Any() //To make sure that we do not create 2 instances of our database

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}

interface DatabaseOperationsListener {
    fun onDatabaseOperationsStarted()
    fun onDatabaseOperationsSuccess()
    fun onDatabaseOperationsFailed()
    fun onDatabaseOperationsCancelled()
}