package com.ozgurerdogan.kotlin_artbooktest.dependencyinjection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozgurerdogan.kotlin_artbooktest.R
import com.ozgurerdogan.kotlin_artbooktest.api.RetrofitApi
import com.ozgurerdogan.kotlin_artbooktest.repo.ArtRepository
import com.ozgurerdogan.kotlin_artbooktest.repo.ArtRepositoryInterface
import com.ozgurerdogan.kotlin_artbooktest.roomdp.ArtDao
import com.ozgurerdogan.kotlin_artbooktest.roomdp.ArtDatabase
import com.ozgurerdogan.kotlin_artbooktest.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context:Context)=Room.databaseBuilder(
        context, ArtDatabase::class.java,"ArtBookDb")
        .build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase)=database.artDao()


    @Singleton
    @Provides
    fun injectRetrofitApi()=Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(RetrofitApi::class.java)

    @Singleton
    @Provides
    fun injectNormalRepo(dao:ArtDao, api:RetrofitApi)=ArtRepository(dao,api) as ArtRepositoryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context:Context)= Glide
        .with(context)
        .setDefaultRequestOptions(RequestOptions()
        .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground ))
}