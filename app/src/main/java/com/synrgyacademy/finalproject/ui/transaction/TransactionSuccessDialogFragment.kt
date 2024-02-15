package com.synrgyacademy.finalproject.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentTransactionSuccessDialogBinding

class TransactionSuccessDialogFragment(private val orderId: String) : DialogFragment() {

    private var _binding: FragmentTransactionSuccessDialogBinding? = null
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
            FragmentTransactionSuccessDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOke.setOnClickListener {
            findNavController().navigate(R.id.transactionSuccessFragment, Bundle().apply {
                putString("orderId", orderId)
            })

            dismiss()
        }

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Navigate to TicketFragment
            findNavController().navigate(R.id.ticket_navigation)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TAG = "TransactionSuccessDialogFragment"
    }
}