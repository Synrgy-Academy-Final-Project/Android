package com.synrgyacademy.finalproject.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentHelpCentreBinding

class HelpCentreFragment : Fragment() {

    private var _binding: FragmentHelpCentreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHelpCentreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
    }

    private fun onClick() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            ivArrowRight.setOnClickListener {
                if(forgotDownloadInvoiceContent.visibility == View.VISIBLE) {
                    ivArrowRight.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_angle_right, null))
                    forgotDownloadInvoiceContent.visibility = View.GONE
                } else {
                    ivArrowRight.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_down, null))
                    forgotDownloadInvoiceContent.visibility = View.VISIBLE
                }
            }

            ivArrowRightEticket.setOnClickListener {
                if (eticketContent.visibility == View.VISIBLE) {
                    ivArrowRightEticket.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_angle_right, null))
                    eticketContent.visibility = View.GONE
                } else {
                    ivArrowRightEticket.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_down, null))
                    eticketContent.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}