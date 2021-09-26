package com.ozgurerdogan.kotlin_artbooktest.roomdp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art:Art)

    @Delete
    suspend fun deleteArt(art:Art)

    @Query("SELECT*FROM arts")
    fun observeArts():LiveData<List<Art>>  //LiveData zaten asenkron çalıştığı için suspend yazmaaya ihtiyaç duyulmadı.
}