package com.synrgyacademy.finalproject.ui.ticket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentClassDialogBinding

class ClassDialogFragment : DialogFragment() {

    // Define the interface
    interface OnClassSelectedListener {
        fun onClassSelected(selectedClass: String)
    }

    // Declare a variable of the interface type
    private var listener: OnClassSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Set the listener to the parent fragment
        listener = parentFragment as? OnClassSelectedListener
        if (listener == null) {
            throw ClassCastException("$parentFragment must implement OnClassSelectedListener")
        }
    }

    private var _binding: FragmentClassDialogBinding? = null

    private val binding get() = _binding!!

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedClass = when (checkedId) {
                R.id.rb_economic -> "Economic"
                R.id.rb_business -> "Business"
                R.id.rb_main -> "First Class"
                else -> ""
            }
            // Call the interface method to pass the selected class data
            listener?.onClassSelected(selectedClass)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "class dialog"
    }
}