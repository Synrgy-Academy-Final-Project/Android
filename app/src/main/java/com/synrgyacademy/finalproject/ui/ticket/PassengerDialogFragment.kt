package com.synrgyacademy.finalproject.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentPassengerDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerDialogFragment : DialogFragment() {
    private var _binding: FragmentPassengerDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AirportViewModel by activityViewModels()

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adultCount = 1
        var childCount = 0
        var babyCount = 0

        binding.tvAdultCount.text = adultCount.toString()
        binding.ibDecreaseAdultCount.isEnabled = adultCount > 1
        binding.tvChildCount.text = childCount.toString()
        binding.ibDecreaseChildCount.isEnabled = childCount > 0
        binding.tvBabyCount.text = babyCount.toString()
        binding.ibDecreaseBabyCount.isEnabled = babyCount > 0

        binding.ibDecreaseAdultCount.setOnClickListener {
           adultCount--
            binding.tvAdultCount.text = adultCount.toString()
            binding.ibDecreaseAdultCount.isEnabled = adultCount > 1
        }
        binding.ibDecreaseChildCount.setOnClickListener {
            childCount--
            binding.tvChildCount.text = childCount.toString()
            binding.ibDecreaseChildCount.isEnabled = childCount > 0
        }
        binding.ibDecreaseBabyCount.setOnClickListener {
            babyCount--
            binding.tvBabyCount.text = babyCount.toString()
            binding.ibDecreaseBabyCount.isEnabled = babyCount > 0
        }
        binding.ibAddAdultCount.setOnClickListener {
            adultCount++
            binding.tvAdultCount.text = adultCount.toString()
            binding.ibDecreaseAdultCount.isEnabled = adultCount > 1
        }
        binding.ibAddChildCount.setOnClickListener {
            childCount++
            binding.tvChildCount.text = childCount.toString()
            binding.ibDecreaseChildCount.isEnabled = childCount > 0
        }
        binding.ibAddBabyCount.setOnClickListener {
            babyCount++
            binding.tvBabyCount.text = babyCount.toString()
            binding.ibDecreaseBabyCount.isEnabled = babyCount > 0
        }

        binding.btnSimpan.setOnClickListener {
            viewModel.setPassengerTotal(adultCount, childCount, babyCount)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassengerDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "passenger dialog"
    }
}