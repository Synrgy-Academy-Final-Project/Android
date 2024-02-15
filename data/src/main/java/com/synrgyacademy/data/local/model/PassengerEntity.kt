package com.synrgyacademy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgyacademy.domain.model.passenger.PassengerDataModel

@Entity(tableName = "passenger")
data class PassengerEntity(
    @PrimaryKey(autoGenerate = true)
    val idUser: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "born_date")
    val bornDate: String,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String? = null,

    @ColumnInfo(name = "type")
    val type: String,
) {
    fun toPassengerDataModel(): PassengerDataModel {
        return PassengerDataModel(
            id = idUser,
            name = name,
            bornDate = bornDate,
            gender = gender,
            phoneNumber = phoneNumber,
            type = type
        )
    }

    companion object {
        fun from(passengerDataModel: PassengerDataModel): PassengerEntity {
            return PassengerEntity(
                idUser = passengerDataModel.id,
                name = passengerDataModel.name,
                bornDate = passengerDataModel.bornDate,
                gender = passengerDataModel.gender,
                phoneNumber = passengerDataModel.phoneNumber,
                type = passengerDataModel.type
            )
        }

        fun List<PassengerEntity>.toPassengerDataModelList(): List<PassengerDataModel> {
            return map { it.toPassengerDataModel() }
        }
    }
}