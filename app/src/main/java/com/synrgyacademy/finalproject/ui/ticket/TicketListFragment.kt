package com.synrgyacademy.finalproject.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.sidesheet.SideSheetBehavior
import com.google.android.material.sidesheet.SideSheetCallback
import com.google.android.material.sidesheet.SideSheetDialog
import com.google.android.material.textview.MaterialTextView
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.utils.dataStore
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.FilterDataModel
import com.synrgyacademy.domain.model.passenger.PassengerTotal
import com.synrgyacademy.domain.model.query.GetScheduleFlightQuery
import com.synrgyacademy.domain.model.query.MinimumPriceQuery
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.adapter.DateTicketAdapter
import com.synrgyacademy.finalproject.adapter.PromoAdapter
import com.synrgyacademy.finalproject.adapter.TicketAdapter
import com.synrgyacademy.finalproject.databinding.FragmentTicketListBinding
import com.synrgyacademy.finalproject.ui.login.NotLoginDialogFragment
import com.synrgyacademy.finalproject.utils.CurrencyUtils.toIDR
import com.synrgyacademy.finalproject.utils.ViewUtils.setBottomMargin
import com.synrgyacademy.finalproject.utils.parcelable
import com.synrgyacademy.finalproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class TicketListFragment : Fragment(), NewFlightDialogFragment.OnScheduleSearchListener {

    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AirportViewModel by viewModels<AirportViewModel>()

    private val dateTicketAdapter: DateTicketAdapter by lazy { DateTicketAdapter() }
    private val promoAdapter: PromoAdapter by lazy { PromoAdapter() }

    private val ticketAdapter: TicketAdapter by lazy { TicketAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomMargin(binding.root)
        setData()
        setRecyclerView()
        onClick()
    }

    private fun onClick() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            filterButton.setOnClickListener {
                showSideSheet()
            }

            tvFilter.setOnClickListener {
                showSideSheet()
            }

            promoButton.setOnClickListener {
                showPromotions()
            }

            tvPromo.setOnClickListener {
                showPromotions()
            }

            flightDropdown.setOnClickListener {
                NewFlightDialogFragment().show(childFragmentManager, NewFlightDialogFragment.TAG)
            }
        }
    }

    private fun setRecyclerView() {
        binding.departureSchedule.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = dateTicketAdapter
        }

        binding.departureList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ticketAdapter
        }
    }

    private fun setData() {
        val getDeparture = arguments?.parcelable("departureData") as? AirportDataModel
        val getArrival = arguments?.parcelable("arrivalData") as? AirportDataModel
        val getDateFlight = arguments?.getString("date")
        val getClass = arguments?.getString("class")
        val getPassenger = arguments?.parcelable("passengerTotal") as? PassengerTotal

        binding.flightsFrom.text = getDeparture?.airportCityCode
        binding.flightsTo.text = getArrival?.airportCityCode
        binding.flightClass.text = getClass

        val totalPassenger =
            (getPassenger?.adult ?: 0) + (getPassenger?.child ?: 0) + (getPassenger?.infant ?: 0)
        binding.flightPersonCount.text =
            getString(R.string.text_passenger_with_value, totalPassenger)

        if (getDeparture != null && getArrival != null && getDateFlight != null && getClass != null) {
            viewModel.getScheduleFlight(
                GetScheduleFlightQuery(
                    getDeparture.airportCode,
                    getArrival.airportCode,
                    getDateFlight,
                    getClass
                )
            )
        }

        viewModel.scheduleState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScheduleState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ScheduleState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is ScheduleState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (state.data.isEmpty()) {
                        binding.tvErrorMessage.visibility = View.VISIBLE
                    } else {
                        binding.tvErrorMessage.visibility = View.GONE
                        viewModel.getMinimumPrice(
                            minimumPriceQuery = MinimumPriceQuery(
                                getDeparture?.airportCode ?: "",
                                getArrival?.airportCode ?: "",
                                getDateFlight ?: "",
                                getClass ?: ""
                            )
                        )
                        ticketAdapter.submitList(state.data)
                    }
                }
            }
        }

        viewModel.minimumPrice.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MinimumPriceState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is MinimumPriceState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is MinimumPriceState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    dateTicketAdapter.submitList(state.data)
                }
            }
        }

        dateTicketAdapter.onclick = {
            viewModel.getScheduleFlight(
                GetScheduleFlightQuery(
                    getDeparture?.airportCode ?: "",
                    getArrival?.airportCode ?: "",
                    it.date,
                    getClass ?: ""
                )
            )
        }

        ticketAdapter.onclick = {
            runBlocking {
                val isLogin =
                    requireContext().dataStore.data.map { it[SessionManager.KEY_LOGIN] ?: false }
                        .first()
                if (isLogin) {
                    val bundle = Bundle().apply {
                        putParcelable("departureData", getDeparture)
                        putParcelable("arrivalData", getArrival)
                        putString("flightDate", getDateFlight)
                        putString("flightClass", getClass)
                        putParcelable("passengerTotal", getPassenger)
                        putParcelable("scheduleData", it)
                    }
                    findNavController().navigate(R.id.detailTicketFragment, bundle)
                } else {
                    NotLoginDialogFragment().show(childFragmentManager, NotLoginDialogFragment.TAG)
                }
            }
        }
    }

    private fun showSideSheet() {
        val inflater = layoutInflater.inflate(R.layout.layout_filter, null)

        val sideSheetDialog = setupSideSheetDialog(inflater)
        setupTimeSelection(inflater)
        setupRangeSeekBar(inflater)
        setupBtnSimpan(inflater, sideSheetDialog)

        sideSheetDialog.setContentView(inflater)
        sideSheetDialog.show()
    }

    private fun setupSideSheetDialog(inflater: View): SideSheetDialog {
        val sideSheetDialog = SideSheetDialog(requireContext())
        sideSheetDialog.behavior.addCallback(object : SideSheetCallback() {
            override fun onStateChanged(sheet: View, newState: Int) {
                if (newState == SideSheetBehavior.STATE_DRAGGING) {
                    sideSheetDialog.behavior.state = SideSheetBehavior.STATE_EXPANDED
                }
            }

            override fun onSlide(sheet: View, slideOffset: Float) {}
        })

        viewModel.getFilterSearch()
        viewModel.getFilter.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GetFilterState.Loading -> {
                    // do nothing
                }

                is GetFilterState.Error -> {
                    // do nothing
                }

                is GetFilterState.Success -> {
                    // Get references to the time selection views
                    val morningView = inflater.findViewById<LinearLayoutCompat>(R.id.morning)
                    val noonView = inflater.findViewById<LinearLayoutCompat>(R.id.noon)
                    val afternoonView = inflater.findViewById<LinearLayoutCompat>(R.id.afternoon)
                    val nightView = inflater.findViewById<LinearLayoutCompat>(R.id.night)

                    // List of all time selection views
                    val timeSelections = listOf(morningView, noonView, afternoonView, nightView)

                    // Reset all backgrounds to bg_rounded_10_greylight
                    timeSelections.forEach { it.setBackgroundResource(R.drawable.bg_rounded_10_greylight) }

                    // Change the background of the selected time to bg_rounded_10_stroke_blue
                    when (state.data.time) {
                        "Pagi" -> morningView.setBackgroundResource(R.drawable.bg_rounded_10_stroke_blue)
                        "Siang" -> noonView.setBackgroundResource(R.drawable.bg_rounded_10_stroke_blue)
                        "Sore" -> afternoonView.setBackgroundResource(R.drawable.bg_rounded_10_stroke_blue)
                        "Malam" -> nightView.setBackgroundResource(R.drawable.bg_rounded_10_stroke_blue)
                    }

                    val rangeSeekbar = inflater.findViewById<RangeSeekBar>(R.id.rangeSeekBar)

                    rangeSeekbar.setProgress(
                        state.data.priceStart ?: 0f,
                        state.data.priceEnd ?: 10000000f
                    )

                    val hargaMin = inflater.findViewById<MaterialTextView>(R.id.harga_min_value)
                    hargaMin.text = rangeSeekbar.leftSeekBar.progress.toIDR()
                    hargaMin.tag = rangeSeekbar.leftSeekBar.progress

                    val hargaMax = inflater.findViewById<MaterialTextView>(R.id.harga_max_value)
                    hargaMax.text = rangeSeekbar.rightSeekBar.progress.toIDR()
                    hargaMax.tag = rangeSeekbar.rightSeekBar.progress

                    inflater.findViewById<MaterialCheckBox>(R.id.checkbox_bagasi).isChecked =
                        state.data.bagasi == true
                    inflater.findViewById<MaterialCheckBox>(R.id.checkbox_entertainment).isChecked =
                        state.data.hiburan == true
                    inflater.findViewById<MaterialCheckBox>(R.id.checkbox_food).isChecked =
                        state.data.makanan == true
                    inflater.findViewById<MaterialCheckBox>(R.id.checkbox_usb).isChecked =
                        state.data.stopkontak == true
                    inflater.findViewById<MaterialCheckBox>(R.id.checkbox_wifi).isChecked =
                        state.data.wifi == true
                    inflater.findViewById<MaterialCheckBox>(R.id.checkbox_refund).isChecked =
                        state.data.refundable == true
                    inflater.findViewById<MaterialCheckBox>(R.id.checkbox_reschedule).isChecked =
                        state.data.reschedule == true
                }
            }
        }

        val btnClose = inflater.findViewById<ShapeableImageView>(R.id.back_button)
        btnClose.setOnClickListener { sideSheetDialog.dismiss() }

        val btnReset = inflater.findViewById<MaterialTextView>(R.id.tv_reset)
        btnReset.setOnClickListener { resetFilters(inflater) }

        val savedFilter = inflater.findViewById<MaterialCheckBox>(R.id.checkbox_saved_filter)
        savedFilter.setOnClickListener {
            viewModel.savedFilter(
                FilterDataModel(
                    time = getDepartureTime(inflater),
                    priceStart = inflater.findViewById<MaterialTextView>(R.id.harga_min_value).tag.toString()
                        .toFloat(),
                    priceEnd = inflater.findViewById<MaterialTextView>(R.id.harga_max_value).tag.toString()
                        .toFloat(),
                    bagasi = getCheckboxValue(inflater, R.id.checkbox_bagasi) != null,
                    hiburan = getCheckboxValue(inflater, R.id.checkbox_entertainment) != null,
                    makanan = getCheckboxValue(inflater, R.id.checkbox_food) != null,
                    stopkontak = getCheckboxValue(inflater, R.id.checkbox_usb) != null,
                    wifi = getCheckboxValue(inflater, R.id.checkbox_wifi) != null,
                    refundable = getCheckboxValue(inflater, R.id.checkbox_refund) != null,
                    reschedule = getCheckboxValue(inflater, R.id.checkbox_reschedule) != null
                )
            )
            requireContext().showToast("Filter berhasil disimpan")
        }

        sideSheetDialog.setCancelable(false)
        sideSheetDialog.setCanceledOnTouchOutside(true)

        return sideSheetDialog
    }

    private fun setupTimeSelection(inflater: View) {
        val timeSelections = listOf(
            inflater.findViewById<LinearLayoutCompat>(R.id.morning),
            inflater.findViewById(R.id.noon),
            inflater.findViewById(R.id.afternoon),
            inflater.findViewById(R.id.night)
        )

        timeSelections.forEachIndexed { index, view ->
            view.setOnClickListener {
                timeSelections.forEachIndexed { j, otherView ->
                    otherView.setBackgroundResource(
                        if (index == j) R.drawable.bg_rounded_10_stroke_blue else R.drawable.bg_rounded_10_greylight
                    )
                }
            }
        }
    }

    private fun setupRangeSeekBar(inflater: View) {
        val rangeSeekBar = inflater.findViewById<RangeSeekBar>(R.id.rangeSeekBar)
        val hargaMin = inflater.findViewById<MaterialTextView>(R.id.harga_min_value)
        val hargaMax = inflater.findViewById<MaterialTextView>(R.id.harga_max_value)

        hargaMin.text = rangeSeekBar.leftSeekBar.progress.toIDR()
        hargaMin.tag = rangeSeekBar.leftSeekBar.progress

        hargaMax.text = rangeSeekBar.rightSeekBar.progress.toIDR()
        hargaMax.tag = rangeSeekBar.rightSeekBar.progress

        rangeSeekBar.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onRangeChanged(
                rangeSeekBar: RangeSeekBar,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                hargaMin.text = leftValue.toIDR()
                hargaMin.tag = leftValue

                hargaMax.text = rightValue.toIDR()
                hargaMax.tag = rightValue
            }

            override fun onStartTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {}
            override fun onStopTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {}
        })
    }

    private fun setupBtnSimpan(inflater: View, sideSheetDialog: SideSheetDialog) {
        val btnSimpan = inflater.findViewById<Button>(R.id.btn_simpan)
        val getDeparture = arguments?.parcelable("departureData") as? AirportDataModel
        val getArrival = arguments?.parcelable("arrivalData") as? AirportDataModel
        val getDateFlight = arguments?.getString("date")
        val getClass = arguments?.getString("class")
        val hargaMin = inflater.findViewById<MaterialTextView>(R.id.harga_min_value)
        val hargaMax = inflater.findViewById<MaterialTextView>(R.id.harga_max_value)

        btnSimpan.setOnClickListener {
            viewModel.getScheduleFlight(
                GetScheduleFlightQuery(
                    departureCode = getDeparture!!.airportCode,
                    arrivalCode = getArrival!!.airportCode,
                    departureDate = getDateFlight!!,
                    airplaneClass = getClass!!,
                    fromPrice = hargaMin.tag.toString(),
                    toPrice = hargaMax.tag.toString(),
                    departureTime = getDepartureTime(inflater),
                    baggage = getCheckboxValue(inflater, R.id.checkbox_bagasi),
                    entertainment = getCheckboxValue(inflater, R.id.checkbox_entertainment),
                    meals = getCheckboxValue(inflater, R.id.checkbox_food),
                    usb = getCheckboxValue(inflater, R.id.checkbox_usb),
                    wifi = getCheckboxValue(inflater, R.id.checkbox_wifi),
                    refund = getCheckboxValue(inflater, R.id.checkbox_refund),
                    reschedule = getCheckboxValue(inflater, R.id.checkbox_reschedule)
                )
            )
            sideSheetDialog.dismiss()
        }
    }

    private fun getDepartureTime(inflater: View): String? {
        val timeSelections = listOf(
            inflater.findViewById<LinearLayoutCompat>(R.id.morning),
            inflater.findViewById(R.id.noon),
            inflater.findViewById(R.id.afternoon),
            inflater.findViewById(R.id.night)
        )

        val times = listOf("Pagi", "Siang", "Sore", "Malam")

        return timeSelections.indexOfFirst { view ->
            view.background.constantState == ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_rounded_10_stroke_blue,
                null
            )?.constantState
        }.takeIf { it != -1 }?.let { times[it] }
    }

    private fun getCheckboxValue(inflater: View, id: Int): String? {
        return if (inflater.findViewById<MaterialCheckBox>(id).isChecked) "yes" else null
    }

    private fun resetFilters(inflater: View) {
        // Reset time selections
        val timeSelections = listOf(
            inflater.findViewById<LinearLayoutCompat>(R.id.morning),
            inflater.findViewById(R.id.noon),
            inflater.findViewById(R.id.afternoon),
            inflater.findViewById(R.id.night)
        )
        timeSelections.forEach { it.setBackgroundResource(R.drawable.bg_rounded_10_greylight) }

        // Reset range seek bar
        val rangeSeekBar = inflater.findViewById<RangeSeekBar>(R.id.rangeSeekBar)
        rangeSeekBar.setProgress(0f, 10000000f)

        // Reset checkboxes
        val checkboxes = listOf(
            R.id.checkbox_bagasi,
            R.id.checkbox_entertainment,
            R.id.checkbox_food,
            R.id.checkbox_usb,
            R.id.checkbox_wifi,
            R.id.checkbox_refund,
            R.id.checkbox_reschedule
        )
        checkboxes.forEach { id ->
            val checkbox = inflater.findViewById<MaterialCheckBox>(id)
            checkbox.isChecked = false
        }
    }

    private fun showPromotions() {
        SideSheetDialog(requireContext()).apply {
            setContentView(R.layout.layout_promotions)
            findViewById<ShapeableImageView>(R.id.back_button)?.setOnClickListener {
                dismiss()
            }

            viewModel.getAllPromotions()
            viewModel.allPromotion.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is AllPromotionsState.Loading -> {
                        findViewById<View>(R.id.progress_bar_promo)?.visibility = View.VISIBLE
                    }

                    is AllPromotionsState.Error -> {
                        findViewById<View>(R.id.progress_bar_promo)?.visibility = View.GONE
                    }

                    is AllPromotionsState.Success -> {
                        findViewById<View>(R.id.progress_bar_promo)?.visibility = View.GONE
                        promoAdapter.submitList(state.data)
                    }
                }
            }

            // Find the RecyclerView and set its layout manager and adapter
            val promotionsRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_promotions)
            promotionsRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
            promotionsRecyclerView?.adapter = promoAdapter

            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSchedule(
        departureData: AirportDataModel,
        arrivalData: AirportDataModel,
        date: String,
        classType: String,
        passengerTotal: PassengerTotal
    ) {
        viewModel.getScheduleFlight(
            GetScheduleFlightQuery(
                departureData.airportCode,
                arrivalData.airportCode,
                date,
                classType
            )
        )

        viewModel.scheduleState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScheduleState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ScheduleState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is ScheduleState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (state.data.isEmpty()) {
                        binding.tvErrorMessage.visibility = View.VISIBLE
                    } else {
                        binding.tvErrorMessage.visibility = View.GONE
                        viewModel.getMinimumPrice(
                            minimumPriceQuery = MinimumPriceQuery(
                                departureData.airportCode,
                                arrivalData.airportCode,
                                date,
                                classType
                            )
                        )
                        ticketAdapter.submitList(state.data)
                    }
                }
            }
        }

        viewModel.minimumPrice.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MinimumPriceState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is MinimumPriceState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }

                is MinimumPriceState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    dateTicketAdapter.selectedItem = 0
                    dateTicketAdapter.submitList(state.data)
                    binding.departureSchedule.post {
                        dateTicketAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        val passengerValue = passengerTotal.adult + passengerTotal.child + passengerTotal.infant
        binding.apply {
            flightsFrom.text = departureData.airportCityCode
            flightsTo.text = arrivalData.airportCityCode
            flightClass.text = classType
            flightPersonCount.text = getString(R.string.text_passenger_with_value, passengerValue)
        }
    }
}