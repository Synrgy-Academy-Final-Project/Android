package com.synrgyacademy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgyacademy.domain.model.tourism.TourismDataModel

@Entity("tourism_place")
data class TourismEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "tourismName")
    val tourismName: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "tourismLocation")
    val tourismLocation: String,

    @ColumnInfo(name = "imageLink")
    val imageLink: String,

    @ColumnInfo(name = "likeTotal")
    val likeTotal: Int,

    @ColumnInfo(name = "createAt")
    val createAt: String,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String
) {
    fun toTourismDataModel() = TourismDataModel(
        id = id,
        tourismName = tourismName,
        description = description,
        tourismLocation = tourismLocation,
        imageLink = imageLink,
        likeTotal = likeTotal,
        createAt = createAt,
        updatedAt = updatedAt
    )
}