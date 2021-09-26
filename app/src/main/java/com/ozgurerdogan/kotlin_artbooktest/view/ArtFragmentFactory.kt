package com.ozgurerdogan.kotlin_artbooktest.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.ozgurerdogan.kotlin_artbooktest.adapter.ArtRecyclerAdapter
import com.ozgurerdogan.kotlin_artbooktest.adapter.ImageRecyclerAdapter
import javax.inject.Inject



class ArtFragmentFactory @Inject constructor(
    private val glide:RequestManager,
    private val artRecyclerAdapter:ArtRecyclerAdapter,
    private val imageRecyclerAdapter:ImageRecyclerAdapter
    ):FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtFragment::class.java.name->ArtFragment(artRecyclerAdapter)
            XmageApiFragment::class.java.name->XmageApiFragment(imageRecyclerAdapter)
            ArtDetailsFragment::class.java.name->ArtDetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }

    }

}



