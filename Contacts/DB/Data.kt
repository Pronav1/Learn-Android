package com.example.contact.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity// this is table and the attributes are the column
data class Data (
    val firstName: String,
    val lastName : String,
    val phoneNumber : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
    )