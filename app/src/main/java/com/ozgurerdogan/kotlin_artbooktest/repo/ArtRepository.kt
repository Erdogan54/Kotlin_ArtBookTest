package com.ozgurerdogan.kotlin_artbooktest.repo

import androidx.lifecycle.LiveData
import com.ozgurerdogan.kotlin_artbooktest.api.RetrofitApi
import com.ozgurerdogan.kotlin_artbooktest.dependencyinjection.Resource
import com.ozgurerdogan.kotlin_artbooktest.model.ImageResponse
import com.ozgurerdogan.kotlin_artbooktest.roomdp.Art
import com.ozgurerdogan.kotlin_artbooktest.roomdp.ArtDao
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi
    ) :ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
                    val response=retrofitApi.imageSearch(imageString)
                    if (response.isSuccessful){
                        response.body()?.let {
                            return@let Resource.success(it)
                        } ?: Resource.error("Error",null)
                    }else{
                        Resource.error("Error",null)
                    }
                }catch (e:Exception){
                    Resource.error("No Data!!",null)
                }
    }
}