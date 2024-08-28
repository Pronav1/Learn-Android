package com.example.contact.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoDB {
    @Insert
    fun insertData(data : Data)

    @Update
    fun updateData(data: Data)

@Delete
fun deleteData(data : Data)

@Query("SELECT * FROM data ORDER BY firstName ASC")
    fun getContactOrderByFirstName (): Flow<List<Data>>


    @Query("SELECT * FROM data ORDER BY lastName ASC")
    fun getContactOrderByLastName (): Flow<List<Data>>

    @Query("SELECT * FROM data ORDER BY phoneNumber ASC")
    fun getContactsOrderedByPhoneNumber (): Flow<List<Data>>

}