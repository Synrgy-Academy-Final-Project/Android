package com.synrgyacademy.finalproject.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.adapter.PopularPlacesVerticalAdapter
import com.synrgyacademy.finalproject.databinding.FragmentPopularPlacesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularPlacesFragment : Fragment() {

    private var _binding: FragmentPopularPlacesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularViewModel by viewModels<PopularViewModel>()
    private val popularPlacesAdapter: PopularPlacesVerticalAdapter by lazy { PopularPlacesVerticalAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPopularPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun onClick() {

        binding.rvPopularPlaces.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = popularPlacesAdapter
        }

        popularPlacesAdapter.onclick = {
            findNavController().navigate(R.id.detailPopularPlacesFragment, Bundle().apply {
                putParcelable("popularPlaces", it)
            })
        }

        binding.svPopularPlaces.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchTourism(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchTourism(newText.toString())
                return false
            }
        })
    }

    private fun setData() {
        viewModel.getAllTourism()
        viewModel.getTourism.observe(viewLifecycleOwner) {
            when (it) {
                is TourismState.Loading -> {
                    // do nothing
                }

                is TourismState.Error -> {
                    // do nothing
                }

                is TourismState.Success -> {
                    popularPlacesAdapter.submitList(it.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}