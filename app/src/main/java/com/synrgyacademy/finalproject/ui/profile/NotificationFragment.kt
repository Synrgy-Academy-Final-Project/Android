package com.synrgyacademy.finalproject.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun setData() {
        profileViewModel.getNotification()
        profileViewModel.getNotification.observe(viewLifecycleOwner) {
            if (it is NotificationState.Success) {
                binding.switchNotification.isChecked = it.data
            }
        }
    }

    private fun onClick() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            switchNotification.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    profileViewModel.saveNotification(true)
                } else {
                    profileViewModel.saveNotification(false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}