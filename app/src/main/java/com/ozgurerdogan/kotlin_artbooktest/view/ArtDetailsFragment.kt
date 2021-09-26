package com.ozgurerdogan.kotlin_artbooktest.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.ozgurerdogan.kotlin_artbooktest.R
import com.ozgurerdogan.kotlin_artbooktest.databinding.FragmentArtDetailsBinding
import com.ozgurerdogan.kotlin_artbooktest.dependencyinjection.Status
import com.ozgurerdogan.kotlin_artbooktest.viewmodel.ArtViewModel
import java.util.*
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide:RequestManager
    ):Fragment(R.layout.fragment_art_details) {

    private var detailsBinding:FragmentArtDetailsBinding?=null
    lateinit var viewModel:ArtViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentArtDetailsBinding.bind(view)
        detailsBinding= binding

        binding.detailsImageView.setOnClickListener{
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToXmageApiFragment())
        }

        val callback=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding.detailsSaveButton.setOnClickListener {
            viewModel.makeArt(
                binding.detailsArtNameText.toString(),
                binding.detailsArtistNameText.toString(),
                binding.detailsYearText.toString())
        }

    }

    private fun subscribeToObservers(){
        viewModel.selectedImageURL.observe(viewLifecycleOwner, androidx.lifecycle.Observer { uri->
            detailsBinding?.let {
                glide.load(uri).into(it.detailsImageView)
            }
        })

        viewModel.inserArtMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status){

                Status.SUCCESS->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()

                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message ?:"Error",Toast.LENGTH_LONG).show()

                }
                Status.LOADING->{

                }

            }
        })
    }




    override fun onDestroyView() {
        detailsBinding=null
        super.onDestroyView()
    }
}