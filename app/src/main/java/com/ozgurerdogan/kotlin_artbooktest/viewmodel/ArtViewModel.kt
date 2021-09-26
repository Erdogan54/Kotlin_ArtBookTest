package com.ozgurerdogan.kotlin_artbooktest.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgurerdogan.kotlin_artbooktest.dependencyinjection.Resource
import com.ozgurerdogan.kotlin_artbooktest.model.ImageResponse
import com.ozgurerdogan.kotlin_artbooktest.repo.ArtRepositoryInterface
import com.ozgurerdogan.kotlin_artbooktest.roomdp.Art
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository:ArtRepositoryInterface
    ): ViewModel() {

    // Art Fragment
    val artList=repository.getArt()

    // Image API Fragment

    private val images=MutableLiveData<Resource<ImageResponse>>()
    val imageList:LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage=MutableLiveData<String>()
    val selectedImageURL:LiveData<String>
        get() =selectedImage

    private var insertArtMsg=MutableLiveData<Resource<Art>>()
    val inserArtMessage:LiveData<Resource<Art>>
        get() =insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg=MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url:String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art:Art)=viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art:Art)=viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name:String,artistName:String,year:String){
        if (name.isEmpty() || artistName.isEmpty()|| year.isEmpty()){
            insertArtMsg.value=Resource.error("Enter name,artist,year",null)
            return
        }

        val yearInt=try {
            year.toInt()
        }catch (e:Exception){
            insertArtMsg.value=Resource.error("Year should be number",null)
            return
        }

        val art=Art(name,artistName,yearInt,selectedImage.value ?:"")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.value=Resource.success(art)
    }

    fun searchForImage(searchString:String){
        if (searchString.isEmpty()){
            return
        }

        images.value=Resource.loading(null)
        viewModelScope.launch {
            val response=repository.searchImage(searchString)
            images.value=response
        }
    }

}