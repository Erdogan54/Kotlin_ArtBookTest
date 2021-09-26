package com.ozgurerdogan.kotlin_artbooktest.roomdp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Art::class),version = 1)
abstract class ArtDatabase:RoomDatabase() {
    abstract fun artDao():ArtDao
}