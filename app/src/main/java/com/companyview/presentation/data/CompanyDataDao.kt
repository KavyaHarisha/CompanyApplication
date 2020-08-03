package com.companyview.presentation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompanyDataDao {

    @Query("SELECT * FROM company ORDER BY companyId DESC")
    fun getCompanyData(): LiveData<List<CompanyData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<CompanyData>)
}
