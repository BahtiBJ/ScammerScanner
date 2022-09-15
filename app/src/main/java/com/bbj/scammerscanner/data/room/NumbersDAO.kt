package com.bbj.scammerscanner.data.room

import androidx.room.*

@Dao
interface NumbersDAO {

    @Query("SELECT * FROM SCAMMERNUMBERS")
    fun getAllFromScammers() : List<ScammerNumbers>

    @Query("SELECT * FROM MAYBESCAMMERNUMBERS")
    fun getAllFromMaybeScammers() : List<MaybeScammerNumbers>

    @Query("SELECT * FROM SUSPICIOUSNUMBERS")
    fun getAllFromSuspicious() : List<SuspiciousNumbers>

    @Query("SELECT * FROM SCAMMERNUMBERS WHERE number = :number")
    fun findInScammer(number : String) : List<ScammerNumbers>

    @Query("SELECT * FROM MAYBESCAMMERNUMBERS WHERE number = :number")
    fun findInMaybeScammers(number : String) : List<MaybeScammerNumbers>

    @Query("SELECT * FROM SUSPICIOUSNUMBERS WHERE number = :number")
    fun findInSuspicious(number : String) : List<SuspiciousNumbers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInScammers(scammerNumbers: ScammerNumbers)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInMaybeScammers(maybeScammerNumbers: MaybeScammerNumbers)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInSuspicious(suspiciousNumbers: SuspiciousNumbers)

    @Query("DELETE FROM SCAMMERNUMBERS WHERE number = :number")
    fun deleteFromScammers(number : String)

    @Query("DELETE FROM maybescammernumbers WHERE number = :number")
    fun deleteFromMaybeScammers(number : String)

    @Query("DELETE FROM SuspiciousNumbers WHERE number = :number")
    fun deleteFromSuspicious(number : String)

}