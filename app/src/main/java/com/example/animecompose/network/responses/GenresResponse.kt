package com.example.animecompose.network.responses

import com.example.animecompose.data.vos.GenresVO
import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<GenresVO>?
)