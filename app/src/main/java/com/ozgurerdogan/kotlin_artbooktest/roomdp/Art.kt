package com.ozgurerdogan.kotlin_artbooktest.roomdp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    var artName:String,
    var artistName:String,
    var year:Int,
    var imageURL:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
)
