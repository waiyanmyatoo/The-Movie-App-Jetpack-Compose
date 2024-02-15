package com.example.animecompose.network.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animecompose.data.vos.ActorVO
import com.google.gson.annotations.SerializedName

@Entity
data class MovieCreditResponse(
    @SerializedName("id")
    @PrimaryKey
    val id: Int? = 0,
    @SerializedName("cast")
    val cast: List<ActorVO>?,
    @SerializedName("crew")
    val crew: List<ActorVO>?,

)
