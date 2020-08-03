package com.companyview.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.companyview.presentation.data.CompanyData
import com.companyview.presentation.data.CompanyDataDao

@Database(entities = [CompanyData::class],
        version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun companyDataDao(): CompanyDataDao


    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "company-db")
                    .addCallback(object : RoomDatabase.Callback() {
                    })
                    .build()
        }
    }
}
