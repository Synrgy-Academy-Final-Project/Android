package com.synrgyacademy.domain.model.airport

data class UserData(
    val email: String? = null,

    val userDetailDataModel: UserDetailDataModel? = null,

    val savedPassenger: List<PassengerSaved>
)

data class PassengerSaved(
    val id: String? = null
)

data class UserDetailDataModel(
    val id: String? = null,

    val firstName: String? = null,

    val lastName: String? = null,

    val address: String? = null,

    val gender: String? = null,

    val phoneNumber: String? = null,

    val visa: String? = null,

    val passport: String? = null,

    val residentPermit: String? = null,

    val dateOfBirth: String? = null,

    val nationality: String? = null,

    val createdDate: String? = null,

    val updatedDate: String? = null,

    val deletedDate: String? = null,

    val nik: String? = null
)

