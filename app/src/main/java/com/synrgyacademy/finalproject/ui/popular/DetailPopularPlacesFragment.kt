package com.synrgyacademy.finalproject.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.synrgyacademy.domain.model.airport.PopularPlacesDataModel
import com.synrgyacademy.finalproject.databinding.FragmentDetailPopularPlacesBinding
import com.synrgyacademy.finalproject.utils.ViewUtils.setBottomMargin
import com.synrgyacademy.finalproject.utils.parcelable

class DetailPopularPlacesFragment : Fragment() {

    private var _binding: FragmentDetailPopularPlacesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailPopularPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setBottomMargin(binding.root)
        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setData() {
        val getPopularPlaces = arguments?.parcelable<PopularPlacesDataModel>("popularPlaces")!!

        binding.apply {
            Glide.with(requireContext())
                .load(getPopularPlaces.image)
                .into(ivPlaces)

            tvPlacesTitle.text = getPopularPlaces.name
            tvPlacesLocation.text = getPopularPlaces.location
            tvPlacesDescription.text = getPopularPlaces.description
            tvLikes.text = getPopularPlaces.likesTotal.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}