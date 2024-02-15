package com.synrgyacademy.finalproject.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.adapter.HistoryOrderAdapter
import com.synrgyacademy.finalproject.databinding.FragmentOrderBinding
import com.synrgyacademy.finalproject.ui.passenger.PassengerState
import com.synrgyacademy.finalproject.ui.passenger.UserDataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by viewModels<OrderViewModel>()
    private val historyOrderAdapter: HistoryOrderAdapter by lazy { HistoryOrderAdapter() }

    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun setData() {
        binding.rvOrder.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyOrderAdapter
        }

        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) {
            when (it) {
                is UserDataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UserDataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is UserDataState.Success -> {
                    token = it.data.token // Assign token value
                    viewModel.getUserDataByToken(it.data.token)
                }
            }
        }

        viewModel.getUserDataByToken.observe(viewLifecycleOwner) {
            when (it) {
                is PassengerState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is PassengerState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.cvNotLogin.visibility = View.VISIBLE
                }

                is PassengerState.Success -> {
                    token?.let { it1 -> viewModel.getHistory(it1) }
                }
            }
        }

        viewModel.getHistory.observe(viewLifecycleOwner) {
            when (it) {
                is OrderState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is OrderState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is OrderState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (it.data.isEmpty()) binding.llEmptyData.visibility =
                        View.VISIBLE else binding.llEmptyData.visibility = View.GONE

                    historyOrderAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun onClick() {
        historyOrderAdapter.onclick = {
            findNavController().navigate(R.id.transactionSuccessFragment, Bundle().apply {
                putString("orderId", it.orderId)
                putString("token", token)
                putString("from", "order")
            })
        }

        binding.btnMasuk.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}