package com.example.contact.DB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Data :: class], version = 1)
abstract class  Database : RoomDatabase(){
abstract val dao : DaoDB
}