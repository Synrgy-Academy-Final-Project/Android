package com.synrgyacademy.finalproject.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.ScheduleDataModel
import com.synrgyacademy.domain.model.passenger.PassengerTotal
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.FragmentDetailTicketBinding
import com.synrgyacademy.finalproject.utils.CurrencyUtils.toIdrFormatWithoutRp
import com.synrgyacademy.finalproject.utils.DateUtils.toEEEDDMMMYYYY
import com.synrgyacademy.finalproject.utils.StringUtils.convertDepartureArrivalTime
import com.synrgyacademy.finalproject.utils.StringUtils.convertFlightTime
import com.synrgyacademy.finalproject.utils.parcelable

class DetailTicketFragment : Fragment() {

    private var _binding: FragmentDetailTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        onClick()
    }

    private fun onClick() {
        binding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            btnLanjut.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("departureData", arguments?.parcelable("departureData"))
                    putParcelable("arrivalData", arguments?.parcelable("arrivalData"))
                    putString("flightDate", arguments?.getString("flightDate"))
                    putString("flightClass", arguments?.getString("flightClass"))
                    putParcelable("passengerTotal", arguments?.parcelable("passengerTotal"))
                    putParcelable("scheduleData", arguments?.parcelable("scheduleData"))
                }
                findNavController().navigate(R.id.inputPassengerFragment, bundle)
            }
        }
    }

    private fun setData() {
        val getDepartureData = arguments?.parcelable<AirportDataModel>("departureData")
        val getArrivalData = arguments?.parcelable<AirportDataModel>("arrivalData")
        val getFlightDate = arguments?.getString("flightDate")
        val getClass = arguments?.getString("flightClass")
        val getPassengerTotal = arguments?.parcelable<PassengerTotal>("passengerTotal")
        val getScheduleData = arguments?.parcelable<ScheduleDataModel>("scheduleData")

        binding.apply {
            tvAirlineName.text = getScheduleData?.companyName
            tvRoute.text = getString(
                R.string.text_route,
                getDepartureData?.airportCityCode,
                getArrivalData?.airportCityCode
            )
            flightFromCity.text = getDepartureData?.airportCityCode
            flightToCity.text = getArrivalData?.airportCityCode
            flightFromAirport.text =
                getDepartureData?.airportCodeName?.substringAfter(" - ")?.replace(" Airport", "")
            flightToAirport.text =
                getArrivalData?.airportCodeName?.substringAfter(" - ")?.replace(" Airport", "")

            Glide.with(requireContext())
                .load(getScheduleData?.urlLogo)
                .into(airlineLogo)

            flightDate.text = getFlightDate?.toEEEDDMMMYYYY()
            flightStart.text = getScheduleData?.departureTime?.convertDepartureArrivalTime()
            flightEnd.text = getScheduleData?.arrivalTime?.convertDepartureArrivalTime()
            flightDuration.text = getScheduleData?.flightTime?.convertFlightTime()

            flightClass.text = getClass
            flightPrice.text = getString(
                R.string.text_total_price_with_slash,
                getScheduleData?.totalPrice?.toIdrFormatWithoutRp()
            )
            tvBaggage.text =
                getString(R.string.text_cabin_item, getScheduleData?.airplaneServices?.cabinBaggage)
            tvBagPerItem.text =
                getString(R.string.text_cabin_item_2, getScheduleData?.airplaneServices?.baggage)

            setServiceStatus(
                getScheduleData?.airplaneServices?.meals == true,
                ivMeal,
                tvMeal,
                R.color.neutral_09,
                R.color.neutral_06,
                getString(R.string.text_meal),
                getString(R.string.text_no_meal)
            )

            setServiceStatus(
                getScheduleData?.airplaneServices?.travelInsurance == true,
                ivAssurance,
                tvAssurance,
                R.color.neutral_09,
                R.color.neutral_06,
                getString(R.string.text_assurance),
                getString(R.string.text_no_assurance)
            )

            setServiceStatus(
                getScheduleData?.airplaneServices?.inflightEntertainment == true,
                ivYoutube,
                tvYoutube,
                R.color.neutral_09,
                R.color.neutral_06,
                getString(R.string.text_entertainment),
                getString(R.string.text_no_entertainment)
            )

            setServiceStatus(
                getScheduleData?.airplaneServices?.electricSocket == true,
                ivUsb,
                tvUsb,
                R.color.neutral_09,
                R.color.neutral_06,
                getString(R.string.text_usb),
                getString(R.string.text_no_usb)
            )

            setServiceStatus(
                getScheduleData?.airplaneServices?.wifi == true,
                ivWifi,
                tvWifi,
                R.color.neutral_09,
                R.color.neutral_06,
                getString(R.string.text_wifi),
                getString(R.string.text_no_wifi)
            )

            setServiceStatus(
                getScheduleData?.airplaneServices?.reschedule == true,
                ivCalendar,
                tvCalendar,
                R.color.secondary_success,
                R.color.neutral_06,
                getString(R.string.text_reschedule_available),
                getString(R.string.text_reschedule_not_available)
            )

            setServiceStatus(
                getScheduleData?.airplaneServices?.refund != 0,
                ivMoney,
                tvMoney,
                R.color.secondary_success,
                R.color.neutral_06,
                getString(R.string.text_refund_up_to, getScheduleData?.airplaneServices?.refund),
                getString(R.string.text_refund_not_available)
            )

            val totalPassenger = (getPassengerTotal?.adult ?: 0) + (getPassengerTotal?.child
                ?: 0) + (getPassengerTotal?.infant ?: 0)

            val basePrice = kotlin.math.ceil(
                ((getScheduleData?.totalPrice ?: 0) * (getPassengerTotal?.adult
                    ?: 0)) + ((getScheduleData?.totalPrice ?: 0) * (getPassengerTotal?.child
                    ?: 0)) + (((getScheduleData?.totalPrice
                    ?: 0) * 0.2) * (getPassengerTotal?.infant ?: 0))
            ).toInt()

            ticketPriceText.text = getString(R.string.text_total_price_person, totalPassenger)
            ticketPriceTotal.text = getString(R.string.text_total_price, basePrice.toIdrFormatWithoutRp())
        }
    }

    private fun setServiceStatus(
        condition: Boolean,
        imageView: ShapeableImageView,
        textView: MaterialTextView,
        trueColor: Int,
        falseColor: Int,
        trueText: String,
        falseText: String
    ) {
        val color = if (condition) trueColor else falseColor
        val text = if (condition) trueText else falseText

        imageView.setColorFilter(ContextCompat.getColor(requireContext(), color))
        textView.setTextColor(ContextCompat.getColor(requireContext(), color))
        textView.text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}