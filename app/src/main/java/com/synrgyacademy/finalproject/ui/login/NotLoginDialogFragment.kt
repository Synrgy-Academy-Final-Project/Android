package com.synrgyacademy.finalproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentNotLoginDialogBinding

class NotLoginDialogFragment : DialogFragment() {

    private var _binding: FragmentNotLoginDialogBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentNotLoginDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "not login dialog"
    }
}