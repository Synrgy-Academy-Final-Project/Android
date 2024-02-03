package com.synrgyacademy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passenger")
data class PassengerEntity(
    @PrimaryKey(autoGenerate = true)
    val idUser: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "honorific")
    val honorific: String,
)
