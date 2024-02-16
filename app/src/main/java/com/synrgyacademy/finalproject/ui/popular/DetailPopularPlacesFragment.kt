package com.synrgyacademy.finalproject.ui.popular

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.synrgyacademy.domain.model.tourism.TourismDataModel
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentDetailPopularPlacesBinding
import com.synrgyacademy.finalproject.utils.ViewUtils.setBottomMargin
import com.synrgyacademy.finalproject.utils.parcelable

class DetailPopularPlacesFragment : Fragment() {

    private var _binding: FragmentDetailPopularPlacesBinding? = null
    private val binding get() = _binding!!

    private var isClicked = false

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
        val getPopularPlaces = arguments?.parcelable<TourismDataModel>("popularPlaces")!!
        var likeTotal = getPopularPlaces.likeTotal

        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

            fabLove.setOnClickListener {

                if (!isClicked) {
                    // First click
                    fabLove.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.primary_blue))
                    fabLove.setImageResource(R.drawable.ic_love_white)
                    likeTotal++
                    isClicked = true
                } else {
                    // Second click
                    fabLove.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                    fabLove.setImageResource(R.drawable.ic_love)
                    likeTotal--
                    isClicked = false
                }

                tvLikes.text = likeTotal.toString()
            }
        }
    }

    private fun setData() {
        val getPopularPlaces = arguments?.parcelable<TourismDataModel>("popularPlaces")!!

        binding.apply {
            Glide.with(requireContext())
                .load(getPopularPlaces.imageLink)
                .into(ivPlaces)

            tvPlacesTitle.text = getPopularPlaces.tourismName
            tvPlacesLocation.text = getPopularPlaces.tourismLocation
            tvPlacesDescription.text = getPopularPlaces.description
            tvLikes.text = getPopularPlaces.likeTotal.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}