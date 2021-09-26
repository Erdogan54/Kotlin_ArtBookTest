package com.ozgurerdogan.kotlin_artbooktest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ozgurerdogan.kotlin_artbooktest.R
import com.ozgurerdogan.kotlin_artbooktest.adapter.ArtRecyclerAdapter
import com.ozgurerdogan.kotlin_artbooktest.databinding.FragmentArtsBinding
import com.ozgurerdogan.kotlin_artbooktest.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
    ): Fragment(R.layout.fragment_arts) {

    private var fragmentbinding:FragmentArtsBinding?=null

    private lateinit var viewModel:ArtViewModel



    private val swipeCallBack=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val myPosition=viewHolder.adapterPosition
            val selectedArt=artRecyclerAdapter.arts[myPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentArtsBinding.bind(view)
        fragmentbinding= binding

        viewModel=ViewModelProvider(this).get(ArtViewModel::class.java)


        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }

        subscribeToObservers()

        binding.recyclerviewArts.adapter=artRecyclerAdapter
        binding.recyclerviewArts.layoutManager=LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerviewArts)
    }



    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts=it
        })
    }



    override fun onDestroyView() {
        fragmentbinding=null
        super.onDestroyView()
    }

}