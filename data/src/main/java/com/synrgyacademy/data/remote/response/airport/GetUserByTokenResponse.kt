package com.synrgyacademy.data.remote.response.airport

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.domain.model.airport.PassengerSaved
import com.synrgyacademy.domain.model.airport.UserData

data class GetUserByTokenResponse(

	@field:SerializedName("data")
	val data: UserDetailData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class UserDetailData(

	@field:SerializedName("usersDetails")
	val usersDetails: UserDetail? = null,

	@field:SerializedName("savedPassenger")
	val savedPassenger: List<SavedPassengerItem>? = null,

	@field:SerializedName("email")
	val email: String? = null
) {
	fun toUserData() = UserData(
		email = email.orEmpty(),
		userDetailDataModel = usersDetails?.toUserDetailDataModel(),
		savedPassenger = savedPassenger?.map { it.toPassengerSaved() } ?: emptyList()
	)
}

data class SavedPassengerItem(

	@field:SerializedName("id")
	val id: String? = null

) {
	fun toPassengerSaved() = PassengerSaved(
		id = id
	)

}