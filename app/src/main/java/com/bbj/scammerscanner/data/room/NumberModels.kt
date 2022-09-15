package com.bbj.scammerscanner.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScammerNumbers( val number : String, val addTime : String, @PrimaryKey(autoGenerate = true) val id : Int = 0)

@Entity
data class MaybeScammerNumbers( val number : String, val addTime : String, @PrimaryKey(autoGenerate = true) val id : Int = 0)

@Entity
data class SuspiciousNumbers( val number : String, val addTime : String, @PrimaryKey(autoGenerate = true) val id : Int = 0)