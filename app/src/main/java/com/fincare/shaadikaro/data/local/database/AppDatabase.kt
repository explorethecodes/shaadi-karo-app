package com.fincare.shaadikaro.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fincare.shaadikaro.data.local.database.converters.Converters
import com.fincare.shaadikaro.data.local.database.daos.PersonsDao
import com.fincare.shaadikaro.data.local.database.entities.Person

private const val DATABASE_NAME = "AppDatabase.db"

@Database(entities = [Person::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPersonsDao(): PersonsDao

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

//interface DatabaseOperationsListener {
//    fun onDatabaseOperationsStarted()
//    fun onDatabaseOperationsSuccess()
//    fun onDatabaseOperationsFailed()
//    fun onDatabaseOperationsCancelled()
//}