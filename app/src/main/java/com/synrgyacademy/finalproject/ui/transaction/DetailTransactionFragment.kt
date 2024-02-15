package com.synrgyacademy.finalproject.ui.transaction

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.utils.dataStore
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.ScheduleDataModel
import com.synrgyacademy.domain.model.passenger.PassengerTotal
import com.synrgyacademy.domain.request.TransactionRequest
import com.synrgyacademy.domain.request.UserDetailData
import com.synrgyacademy.finalproject.MainActivity
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentDetailTransactionBinding
import com.synrgyacademy.finalproject.ui.login.NotLoginDialogFragment
import com.synrgyacademy.finalproject.ui.passenger.AllPassengerLocalState
import com.synrgyacademy.finalproject.ui.passenger.PassengerState
import com.synrgyacademy.finalproject.ui.passenger.PassengerViewModel
import com.synrgyacademy.finalproject.ui.passenger.UserDataState
import com.synrgyacademy.finalproject.ui.profile.NotificationState
import com.synrgyacademy.finalproject.utils.CurrencyUtils.toIdrFormatWithoutRp
import com.synrgyacademy.finalproject.utils.DateUtils.DDMMYYYYStriptoEEEddMMM
import com.synrgyacademy.finalproject.utils.NotificationUtils.createNotification
import com.synrgyacademy.finalproject.utils.StringUtils.convertDepartureArrivalTime
import com.synrgyacademy.finalproject.utils.StringUtils.convertFlightTime
import com.synrgyacademy.finalproject.utils.StringUtils.yyyyMMddTHHmmssZtoDDMMMYYYY
import com.synrgyacademy.finalproject.utils.StringUtils.yyyyMMddTHHmmssZtoHHmmss
import com.synrgyacademy.finalproject.utils.ViewUtils.setBottomMargin
import com.synrgyacademy.finalproject.utils.parcelable
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class DetailTransactionFragment : Fragment() {

    private var _binding: FragmentDetailTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels<TransactionViewModel>()
    private val passengerViewModel: PassengerViewModel by viewModels<PassengerViewModel>()

    private lateinit var getDeparture: AirportDataModel
    private lateinit var getArrival: AirportDataModel
    private lateinit var getDateFlight: String
    private lateinit var getClass: String
    private lateinit var getPassenger: PassengerTotal
    private lateinit var getSchedule: ScheduleDataModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentDetailTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setBottomMargin(binding.root)
        onClick()
        setSpinner()
        checkUserToken()
    }

    private fun onClick() {
        binding.btnBayar.setOnClickListener {
            runBlocking {
                val isLogin =
                    requireContext().dataStore.data.map { it[SessionManager.KEY_LOGIN] ?: false }
                        .first()
                if (isLogin && binding.checkboxAgreement.isChecked) {
                    requireContext().dataStore.data.map { it[SessionManager.KEY_TOKEN] }
                        .first()?.let { it1 ->
                            viewModel.addTransaction(
                                token = it1,
                                transactionRequest = TransactionRequest(
                                    companyName = getSchedule.companyName,
                                    url = getSchedule.urlLogo,
                                    airplaneId = getSchedule.airplaneId,
                                    airplaneName = getSchedule.airplaneName,
                                    airplaneCode = getSchedule.airplaneCode,
                                    airplaneClassId = getSchedule.airplaneClassId,
                                    airplaneClass = getSchedule.airplaneClass,
                                    airplaneTimeFLightId = getSchedule.airplaneFlightTimeId,
                                    departureCode = getSchedule.departureCode,
                                    departureDate = getSchedule.departureTime.yyyyMMddTHHmmssZtoDDMMMYYYY(),
                                    departureTime = getSchedule.departureTime.yyyyMMddTHHmmssZtoHHmmss(),
                                    arrivalCode = getSchedule.arrivalCode,
                                    arrivalDate = getSchedule.arrivalTime.yyyyMMddTHHmmssZtoDDMMMYYYY(),
                                    arrivalTime = getSchedule.arrivalTime.yyyyMMddTHHmmssZtoHHmmss(),
                                    priceFlight = getSchedule.totalPrice.toString(),
                                    codePromo = if (binding.tvDiskonValue.text.toString()
                                            .replace(Regex("[^0-9]"), "").toInt() > 0
                                    ) binding.promoEditText.text.toString() else "",
                                    userDetails = if (passengerViewModel.allPassenger.value is AllPassengerLocalState.Success) {
                                        (passengerViewModel.allPassenger.value as AllPassengerLocalState.Success).data.map {
                                            UserDetailData.fromPassengerDataModel(it)
                                        }
                                    } else {
                                        emptyList()
                                    }
                                )
                            )
                        }
                } else if (!binding.checkboxAgreement.isChecked) {
                    requireContext().showToast("Anda harus menyetujui syarat dan ketentuan")
                } else {
                    NotLoginDialogFragment().show(childFragmentManager, NotLoginDialogFragment.TAG)
                }
            }
        }

        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()

                // Check if the URL contains the required parameters
                if (url.contains("status_code=200") && url.contains("status=settlement")) {
                    // Extract the order_id
                    val orderId = Uri.parse(url).getQueryParameter("order_id")
                    passengerViewModel.deletedPassenger()

                    if (orderId != null) {

                        // Clear all fragments in the back stack
                        for (i in 0 until childFragmentManager.backStackEntryCount) {
                            childFragmentManager.popBackStackImmediate()
                        }

                        viewModel.getNotification()
                        viewModel.getNotification.observe(viewLifecycleOwner) { notificationState ->
                            if (notificationState is NotificationState.Success) {
                                if (notificationState.data) {
                                    createNotification(
                                        requireContext(),
                                        "Transaksi",
                                        "Transaksi berhasil",
                                        "Terima kasih telah melakukan transaksi di Fly.id!",
                                        MainActivity::class.java
                                    )
                                }
                            }
                        }

                        findNavController().navigate(
                            R.id.transactionSuccessFragment,
                            Bundle().apply {
                                putString("orderId", orderId)
                                putString("token", runBlocking {
                                    requireContext().dataStore.data.map { it[SessionManager.KEY_TOKEN] }
                                        .first()
                                })
                            })
                    }

                    // Return true to stop loading the URL
                    return true
                }

                // Return false to continue loading the URL
                return false
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.webview.canGoBack()) {
                binding.webview.goBack()
            } else {
                findNavController().popBackStack()
            }
        }

        binding.btnConfirmationPromoCode.setOnClickListener {
            if (binding.promoEditText.text?.isNotEmpty() == true) viewModel.getPromotion(binding.promoEditText.text.toString())
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.spinnerMetodePembayaran.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()

                    if (selectedItem == "Midtrans") {
                        hideInputCardField(false)
                    } else {
                        hideInputCardField(true)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Another interface callback
                }
            }
    }

    private fun setData() {
        getDeparture = (arguments?.parcelable("departureData") as? AirportDataModel)!!
        getArrival = (arguments?.parcelable("arrivalData") as? AirportDataModel)!!
        getDateFlight = arguments?.getString("flightDate").toString()
        getClass = arguments?.getString("flightClass").toString()
        getPassenger = (arguments?.parcelable("passengerTotal") as? PassengerTotal)!!
        getSchedule = (arguments?.parcelable("scheduleData") as? ScheduleDataModel)!!

        binding.apply {
            Glide.with(requireContext())
                .load(getSchedule.urlLogo)
                .into(airlineLogo)

            flightDate.text = getDateFlight.DDMMYYYYStriptoEEEddMMM()
            flightFromCity.text = getDeparture.airportCityCode
            flightToCity.text = getArrival.airportCityCode
            flightFromAirport.text =
                getDeparture.airportCodeName.substringAfter(" - ").replace(" Airport", "")
            flightToAirport.text =
                getArrival.airportCodeName.substringAfter(" - ").replace(" Airport", "")
            flightStart.text = getSchedule.departureTime.convertDepartureArrivalTime()
            flightEnd.text = getSchedule.arrivalTime.convertDepartureArrivalTime()
            flightDuration.text = getSchedule.flightTime.convertFlightTime()

            val basePrice =
                kotlin.math.ceil((getSchedule.totalPrice * getPassenger.adult) + (getSchedule.totalPrice * getPassenger.child) + ((getSchedule.totalPrice * 0.2) * getPassenger.infant))
                    .toInt()

            tvHargaDasarPrice.text =
                getString(R.string.text_total_price, basePrice.toIdrFormatWithoutRp())
            tvTaxAndOtherPrice.text = getString(R.string.text_total_price, "15.000")
            tvDiskonValue.text = getString(R.string.text_total_price, "0")
            tvPriceBeforeDiskonValue.text =
                getString(R.string.text_total_price, (basePrice + 15000).toIdrFormatWithoutRp())
            tvFinalPrice.text =
                getString(R.string.text_total_price, (basePrice + 15000).toIdrFormatWithoutRp())
        }

        passengerViewModel.getAllPassenger()
        passengerViewModel.allPassenger.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AllPassengerLocalState.Loading -> {
                    // do nothing
                }

                is AllPassengerLocalState.Error -> {
                    requireContext().showToast(state.error)
                }

                is AllPassengerLocalState.Success -> {
                    calculateAndSetPrice(
                        getSchedule.totalPrice,
                        getPassenger.adult,
                        binding.tvDewasaPrice
                    )
                    calculateAndSetPrice(
                        getSchedule.totalPrice,
                        getPassenger.child,
                        binding.tvAnakPrice,
                        binding.tvAnak
                    )
                    calculateAndSetPrice(
                        getSchedule.totalPrice,
                        getPassenger.infant,
                        binding.tvBayiPrice,
                        binding.tvBayi,
                        true
                    )
                }
            }
        }

        viewModel.getPromotions.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PromotionState.Loading -> {
                    // do nothing
                }

                is PromotionState.Error -> {
                    requireContext().showToast("Promo tidak tersedia")
                    val drawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_cross_circle)
                    binding.promoEditText.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        drawable,
                        null
                    )

                    val basePrice =
                        kotlin.math.ceil((getSchedule.totalPrice * getPassenger.adult) + (getSchedule.totalPrice * getPassenger.child) + ((getSchedule.totalPrice * 0.2) * getPassenger.infant))
                            .toInt()

                    binding.tvDiskonValue.text = getString(R.string.text_total_price, "0")
                    binding.tvFinalPrice.text = getString(
                        R.string.text_total_price,
                        (basePrice + 15000).toIdrFormatWithoutRp()
                    )
                }

                is PromotionState.Success -> {
                    val drawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_circle)
                    binding.promoEditText.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        drawable,
                        null
                    )

                    val basePrice =
                        kotlin.math.ceil((getSchedule.totalPrice * getPassenger.adult) + (getSchedule.totalPrice * getPassenger.child) + ((getSchedule.totalPrice * 0.2) * getPassenger.infant))
                            .toInt()
                    val discountAmount =
                        kotlin.math.ceil(basePrice * (state.data.discount / 100.0)).toInt()
                    val priceAfterDiscount = basePrice - discountAmount

                    binding.tvDiskonValue.text = getString(
                        R.string.text_total_price_with_discount,
                        discountAmount.toIdrFormatWithoutRp()
                    )

                    binding.tvFinalPrice.text = getString(
                        R.string.text_total_price,
                        (priceAfterDiscount + 15000).toIdrFormatWithoutRp()
                    )
                }
            }
        }

        viewModel.addTransaction.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TransactionState.Loading -> {
                    // do nothing
                }

                is TransactionState.Error -> {
                    requireContext().showToast(state.error)
                }

                is TransactionState.Success -> {
                    Log.d("URL", state.data.redirectUrl)
                    binding.webview.visibility = View.VISIBLE
                    binding.webview.settings.javaScriptEnabled = true

                    binding.webview.loadUrl(state.data.redirectUrl)
                }
            }
        }
    }

    private fun checkUserToken() {
        passengerViewModel.getUserData()
        passengerViewModel.userData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserDataState.Loading -> {
                    // do nothing
                }

                is UserDataState.Error -> {
                    requireContext().showToast(state.error)
                }

                is UserDataState.Success -> {
                    passengerViewModel.getUserDataByToken(state.data.token)
                }
            }
        }

        passengerViewModel.getUserDataByToken.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PassengerState.Loading -> {
                    // do nothing
                }

                is PassengerState.Error -> {
                    if (state.error == "Kesalahan Server Internal") {
                        passengerViewModel.expiredToken()
                        passengerViewModel.deletedPassenger()
                    }
                }

                is PassengerState.Success -> {
                    // do nothing
                }
            }
        }
    }

    private fun setSpinner() {
        // Reference the Spinner
        val spinner = binding.spinnerMetodePembayaran
        spinner.setSelection(0)

        // Create an ArrayAdapter using a default spinner layout and an array of choices
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.payment_methods_array, // This is an array of choices to display in the Spinner
            R.layout.spinner_dropdown_item
        )

        // Apply the adapter to the Spinner
        spinner.adapter = adapter

        // Check if the default value is "Midtrans"
        val defaultItem = spinner.selectedItem.toString()
        if (defaultItem == "Midtrans") {
            hideInputCardField(false)
        } else {
            hideInputCardField(true)
        }
    }

    private fun hideInputCardField(enable: Boolean) {
        binding.apply {
            tvNamaPemegangKartu.visibility = if (enable) View.VISIBLE else View.GONE
            userNameLayout.visibility = if (enable) View.VISIBLE else View.GONE
            tvNomorKartu.visibility = if (enable) View.VISIBLE else View.GONE
            nomorKartuLayout.visibility = if (enable) View.VISIBLE else View.GONE
            tvCvv.visibility = if (enable) View.VISIBLE else View.GONE
            cvvLayout.visibility = if (enable) View.VISIBLE else View.GONE
            tvExpired.visibility = if (enable) View.VISIBLE else View.GONE
            expiredLayout.visibility = if (enable) View.VISIBLE else View.GONE
        }
    }

    private fun calculateAndSetPrice(
        price: Int?,
        passengerCount: Int?,
        priceTextView: MaterialTextView,
        labelTextView: MaterialTextView? = null,
        isInfant: Boolean = false
    ) {
        if (passengerCount != 0) {
            val finalPrice = if (isInfant) {
                (price ?: 0) * 0.2 * (passengerCount ?: 0)
            } else {
                (price ?: 0) * (passengerCount ?: 0)
            }
            priceTextView.text =
                getString(
                    R.string.text_total_price_with_multiply,
                    finalPrice.toInt().toIdrFormatWithoutRp(),
                    passengerCount
                )
        } else {
            labelTextView?.visibility = View.GONE
            priceTextView.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}