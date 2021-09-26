package com.ozgurerdogan.kotlin_artbooktest.repo

import androidx.lifecycle.LiveData
import com.ozgurerdogan.kotlin_artbooktest.dependencyinjection.Resource
import com.ozgurerdogan.kotlin_artbooktest.model.ImageResponse
import com.ozgurerdogan.kotlin_artbooktest.roomdp.Art

interface ArtRepositoryInterface {

    suspend fun insertArt(art:Art)

    suspend fun deleteArt(art:Art)

    fun getArt():LiveData<List<Art>>

    suspend fun searchImage(image:String):Resource<ImageResponse>

}