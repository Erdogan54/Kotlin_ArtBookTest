package com.ozgurerdogan.kotlin_artbooktest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.ozgurerdogan.kotlin_artbooktest.R
import com.ozgurerdogan.kotlin_artbooktest.adapter.ImageRecyclerAdapter
import com.ozgurerdogan.kotlin_artbooktest.databinding.FragmentXmageApiBinding
import com.ozgurerdogan.kotlin_artbooktest.dependencyinjection.Status
import com.ozgurerdogan.kotlin_artbooktest.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class XmageApiFragment @Inject constructor(
    val imageRecyclerAdapter:ImageRecyclerAdapter
    ):Fragment(R.layout.fragment_xmage_api) {

    private var fragmentBinding:FragmentXmageApiBinding?=null
    lateinit var viewModel:ArtViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentXmageApiBinding.bind(view)
        fragmentBinding=binding

        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        var job: Job?=null


        binding.imageSearchText.addTextChangedListener {
            job?.cancel()
            job=lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObservers()


        binding.imageRecyclerView.adapter=imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager=GridLayoutManager(requireContext(),3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

        subscribeToObservers()
    }

    fun subscribeToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {

            when (it.status){

                Status.SUCCESS->{
                    val urls=it.data?.hits?.map { imageResult -> imageResult.previewURL} // dönüştürüyor
                    imageRecyclerAdapter.images=urls ?: listOf()

                    fragmentBinding?.imageProgressCircular?.visibility=View.GONE

                }

                Status.ERROR->{
                    fragmentBinding?.imageProgressCircular?.visibility=View.GONE
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()

                }

                Status.LOADING->{
                    fragmentBinding?.let {
                        it.imageProgressCircular.visibility=View.GONE
                    }

                }
            }

        })

    }
}