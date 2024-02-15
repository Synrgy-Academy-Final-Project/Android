package com.synrgyacademy.domain.request

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.domain.model.passenger.PassengerDataModel

data class TransactionRequest(
    @field:SerializedName("companyName")
    val companyName: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("airplaneId")
    val airplaneId: String,

    @field:SerializedName("airplaneName")
    val airplaneName: String,

    @field:SerializedName("airplaneCode")
    val airplaneCode: String,

    @field:SerializedName("airplaneClassId")
    val airplaneClassId: String,

    @field:SerializedName("airplaneClass")
    val airplaneClass: String,

    @field:SerializedName("airplaneTimeFLightId")
    val airplaneTimeFLightId: String,

    @field:SerializedName("departureCode")
    val departureCode: String,

    @field:SerializedName("departureDate")
    val departureDate: String,

    @field:SerializedName("departureTime")
    val departureTime: String,

    @field:SerializedName("arrivalCode")
    val arrivalCode: String,

    @field:SerializedName("arrivalDate")
    val arrivalDate: String,

    @field:SerializedName("arrivalTime")
    val arrivalTime: String,

    @field:SerializedName("priceFlight")
    val priceFlight: String,

    @field:SerializedName("codePromo")
    val codePromo: String,

    @field:SerializedName("userDetails")
    val userDetails: List<UserDetailData>,
)

data class UserDetailData(
    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @field:SerializedName("airplaneAdditionalId")
    val airplaneAdditionalId: String? = null,
) {
    companion object {
        fun fromPassengerDataModel(passengerDataModel: PassengerDataModel): UserDetailData {
            val names = passengerDataModel.name.split(" ")
            val firstName: String
            val lastName: String

            if (names.size > 1) {
                lastName = names.last()
                firstName = names.dropLast(1).joinToString(" ")
            } else {
                firstName = names[0]
                lastName = names[0]
            }

            return UserDetailData(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = passengerDataModel.phoneNumber.orEmpty(),
                dateOfBirth = passengerDataModel.bornDate
            )
        }
    }
}