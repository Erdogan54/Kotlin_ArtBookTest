package com.ozgurerdogan.kotlin_artbooktest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozgurerdogan.kotlin_artbooktest.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var artfragmentFactory:ArtFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory=artfragmentFactory
        setContentView(R.layout.activity_main)
    }


}