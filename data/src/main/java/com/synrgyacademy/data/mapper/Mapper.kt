package com.synrgyacademy.data.mapper

import com.synrgyacademy.data.local.model.HistorySearchingEntity
import com.synrgyacademy.data.remote.response.AirplaneServicesItem
import com.synrgyacademy.data.remote.response.AirportListItem
import com.synrgyacademy.data.remote.response.AuthData
import com.synrgyacademy.data.remote.response.MinimumPriceItem
import com.synrgyacademy.data.remote.response.ScheduleData
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.HistoryDataModel
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.model.airport.ScheduleDataModel
import com.synrgyacademy.domain.model.airport.ScheduleServicesItem
import com.synrgyacademy.domain.model.auth.AuthDataModel


fun AuthData.toAuthDataModel(): AuthDataModel =
    AuthDataModel(
        token = token.orEmpty(),
        email = email.orEmpty(),
        roles = roles ?: emptyList(),
        fullName = fullName.orEmpty(),
        type = type.orEmpty(),
        message = message.orEmpty()
    )

fun List<AirportListItem>.toAirportDataModel(): List<AirportDataModel> =
    map {
        AirportDataModel(
            airportCityCode = it.airportCityCode.orEmpty(),
            airportCityCountry = it.airportCityCountry.orEmpty(),
            airportCode = it.airportCode.orEmpty(),
            airportCodeName = it.airportCodeName.orEmpty()
        )
    }

fun List<ScheduleData>.toScheduleDataModel(): List<ScheduleDataModel> =
    map {
        ScheduleDataModel(
            companyName = it.companyName.orEmpty(),
            urlLogo = it.urlLogo.orEmpty(),
            airplaneId = it.airplaneId.orEmpty(),
            airplaneName = it.airplaneName.orEmpty(),
            airplaneCode = it.airplaneCode.orEmpty(),
            airplaneClassId = it.airplaneClassId.orEmpty(),
            airplaneClass = it.airplaneClass.orEmpty(),
            capacity = it.capacity ?: 0,
            airplaneServices = it.airplaneServices.toScheduleServicesItem(),
            airplaneFlightTimeId = it.airplaneFlightTimeId.orEmpty(),
            flightTime = it.flightTime.orEmpty(),
            departureCode = it.departureCode.orEmpty(),
            departureCityCode = it.departureCityCode.orEmpty(),
            departureNameAirport = it.departureNameAirport.orEmpty(),
            arrivalCode = it.arrivalCode.orEmpty(),
            arrivalCityCode = it.arrivalCityCode.orEmpty(),
            arrivalNameAirport = it.arrivalNameAirport.orEmpty(),
            departureTime = it.departureTime.orEmpty(),
            arrivalTime = it.arrivalTime.orEmpty(),
            totalPrice = it.totalPrice ?: 0
        )
    }

fun AirplaneServicesItem.toScheduleServicesItem(): ScheduleServicesItem =
    ScheduleServicesItem(
        baggage = baggage ?: 0,
        cabinBaggage = cabinBaggage ?: 0,
        meals = meals ?: false,
        travelInsurance = travelInsurance ?: false,
        inflightEntertainment = inflightEntertainment ?: false,
        electricSocket = electricSocket ?: false,
        wifi = wifi ?: false,
        reschedule = reschedule ?: false,
        refund = refund ?: 0
    )

fun List<MinimumPriceItem>.toMinimumDataModel(): List<MinimumDataModel> =
    map {
        MinimumDataModel(
            date = it.date.orEmpty(),
            price = it.price ?: 0
        )
    }


// Room Database
fun HistoryDataModel.toHistoryEntity(): HistorySearchingEntity =
    HistorySearchingEntity(
        id = id,
        departureData = departureData,
        arrivalData = arrivalData,
        departureDate = departureDate,
        searchingDate = searchingDate,
        passenger = passenger,
        airplaneClass = airplaneClass
    )