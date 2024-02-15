package com.synrgyacademy.data.remote.response.airport

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.domain.model.airport.UserDetailDataModel

data class UserDetail(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @field:SerializedName("visa")
    val visa: String? = null,

    @field:SerializedName("passport")
    val passport: String? = null,

    @field:SerializedName("residentPermit")
    val residentPermit: String? = null,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,

    @field:SerializedName("nationality")
    val nationality: String? = null,

    @field:SerializedName("createdDate")
    val createdDate: String? = null,

    @field:SerializedName("updatedDate")
    val updatedDate: String? = null,

    @field:SerializedName("deletedDate")
    val deletedDate: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null
) {
    fun toUserDetailDataModel() = UserDetailDataModel(
        id = id.orEmpty(),
        firstName = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        address = address.orEmpty(),
        gender = gender.orEmpty(),
        phoneNumber = phoneNumber.orEmpty(),
        visa = visa.orEmpty(),
        passport = passport.orEmpty(),
        residentPermit = residentPermit.orEmpty(),
        dateOfBirth = dateOfBirth.orEmpty(),
        nationality = nationality.orEmpty(),
        createdDate = createdDate.orEmpty(),
        updatedDate = updatedDate.orEmpty(),
        deletedDate = deletedDate.orEmpty(),
        nik = nik.orEmpty()
    )

}