package com.example.animecompose.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GenresVO(
    @SerializedName("id") @PrimaryKey var id: Int? = 0,
    @SerializedName("name") var name: String?
)
