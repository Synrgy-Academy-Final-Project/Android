package com.synrgyacademy.finalproject.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentTransactionSuccessBinding
import com.synrgyacademy.finalproject.utils.CurrencyUtils.toIdrFormatWithoutRp
import com.synrgyacademy.finalproject.utils.StringUtils.cleanAndUppercase
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionSuccessFragment : Fragment() {

    private var _binding: FragmentTransactionSuccessBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels<TransactionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentTransactionSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun setData() {
        val orderId = arguments?.getString("orderId")!!
        val tokenUser = arguments?.getString("token")!!

        viewModel.historyTransactionById(tokenUser, orderId)
        viewModel.historyTransactionById.observe(viewLifecycleOwner) { result ->
            when (result) {
                is HistoryTransactionState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is HistoryTransactionState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireContext().showToast(result.error)
                }

                is HistoryTransactionState.Success -> {
                    if(result.data.transactionStatus == "Pembayaran Berhasil") {
                        binding.apply {
                            progressBar.visibility = View.GONE

                            if(arguments?.getString("from") != "order") viewModel.getETicketViaEmail(tokenUser, orderId)

                            Glide.with(requireContext())
                                .load(result.data.urlCompany)
                                .into(icAirplaneLogo)

                            textPaymentSuccess.text = "Pembayaran Berhasil"
                            icSuccess.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_success, null))

                            departureDate.text = result.data.departureDate
                            flightFromCity.text = result.data.departureCityCode
                            flightToCity.text = result.data.arrivalCityCode
                            flightFromAirport.text =
                                result.data.departureAirportName?.replace(" Airport", "")
                            flightToAirport.text =
                                result.data.arrivalAirportName?.replace(" Airport", "")
                            flightStart.text = result.data.departureTime
                            flightEnd.text = result.data.arrivalTime
                            flightDuration.text = result.data.durationAirplane

                            textInvoiceNumber.text = result.data.oderCode
                            textPaymentMethod.text = result.data.paymentMethod?.cleanAndUppercase()
                            textTotalPriceValue.text = getString(
                                R.string.text_total_price,
                                result.data.totalPrice?.toIdrFormatWithoutRp()
                            )
                            textStatusValue.text =
                                result.data.transactionStatus ?: "Pembayaran Gagal"
                        }
                    } else {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            textPaymentSuccess.text = "Pembayaran Gagal"
                            icSuccess.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_gagal, null))

                            Glide.with(requireContext())
                                .load(result.data.urlCompany)
                                .into(icAirplaneLogo)
                            departureDate.text = result.data.departureDate
                            flightFromCity.text = result.data.departureCityCode
                            flightToCity.text = result.data.arrivalCityCode
                            flightFromAirport.text =
                                result.data.departureAirportName?.replace(" Airport", "")
                            flightToAirport.text =
                                result.data.arrivalAirportName?.replace(" Airport", "")
                            flightStart.text = result.data.departureTime
                            flightEnd.text = result.data.arrivalTime
                            flightDuration.text = result.data.durationAirplane

                            textInvoiceNumber.text = result.data.oderCode
                            textPaymentMethod.text = result.data.paymentMethod?.cleanAndUppercase()
                            textTotalPriceValue.text = getString(
                                R.string.text_total_price,
                                result.data.totalPrice?.toIdrFormatWithoutRp()
                            )
                            textStatusValue.text = "Pembayaran Gagal"
                        }
                    }
                }
            }
        }
    }

    private fun onClick() {
        binding.buttonBackToHome.setOnClickListener {
            findNavController().apply {
                popBackStack(R.id.ticket_navigation, true)
                navigate(R.id.ticket_navigation)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().apply {
                popBackStack(R.id.ticket_navigation, true)
                navigate(R.id.ticket_navigation)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}