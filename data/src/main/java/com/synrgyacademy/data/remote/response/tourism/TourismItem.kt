package com.synrgyacademy.data.remote.response.tourism

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.data.local.model.TourismEntity
import com.synrgyacademy.domain.model.tourism.TourismDataModel

data class TourismItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("nama_tempat_wisata")
    val tourismName: String,

    @field:SerializedName("deskripsi")
    val description: String,

    @field:SerializedName("lokasi_wisata")
    val tourismLocation: String,

    @field:SerializedName("link_gambar")
    val imageLink: String,

    @field:SerializedName("jumlah_like")
    val likeTotal: Int,

    @field:SerializedName("created_at")
    val createAt: String,

    @field:SerializedName("updated_at")
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

    fun toTourismEntity() = TourismEntity(
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