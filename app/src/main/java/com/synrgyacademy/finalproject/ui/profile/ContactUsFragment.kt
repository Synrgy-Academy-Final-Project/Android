package com.synrgyacademy.finalproject.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.databinding.FragmentContactUsBinding
import com.synrgyacademy.finalproject.utils.showToast

class ContactUsFragment : Fragment() {

    private var _binding: FragmentContactUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContactUsBinding.inflate(inflater, container, false)
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

            tvEmailBtn.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:info@flyid.com") // replace with your email
                }
                if (emailIntent.resolveActivity(requireActivity().packageManager) != null) startActivity(
                    emailIntent
                ) else requireContext().showToast("Tidak ada aplikasi email")
            }

            tvChatBtn.setOnClickListener {
                val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://api.whatsapp.com/send?phone=081222333449") // replace with your WhatsApp number
                }
                if (whatsappIntent.resolveActivity(requireActivity().packageManager) != null) startActivity(
                    whatsappIntent
                ) else requireContext().showToast("Tidak ada aplikasi WhatsApp")
            }

            tvTeleponBtn.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:081222333449") // replace with your phone number
                }
                if (dialIntent.resolveActivity(requireActivity().packageManager) != null) startActivity(
                    dialIntent
                ) else requireContext().showToast("Tidak ada aplikasi telepon")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}