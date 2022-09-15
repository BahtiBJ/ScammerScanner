package com.bbj.scammerscanner.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ScammerNumbers::class, MaybeScammerNumbers::class, SuspiciousNumbers::class],
    version = 1
)
abstract class NumbersDatabase : RoomDatabase() {

    abstract fun getDao() : NumbersDAO

}