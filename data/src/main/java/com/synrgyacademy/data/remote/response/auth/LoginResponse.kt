package com.synrgyacademy.data.remote.response.auth

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.domain.model.auth.LoginDataModel

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginData? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class LoginData(

	@field:SerializedName("roles")
	val roles: List<String>? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
) {
	fun toLoginDataModel(): LoginDataModel {
		return LoginDataModel(
			roles = roles.orEmpty(),
			fullName = fullName.orEmpty(),
			type = type.orEmpty(),
			email = email.orEmpty(),
			token = token.orEmpty()
		)
	}
}
