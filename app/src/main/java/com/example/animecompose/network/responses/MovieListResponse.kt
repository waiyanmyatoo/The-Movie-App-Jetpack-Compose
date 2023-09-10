package com.example.animecompose.network.responses

import com.example.animecompose.data.vos.DateVO
import com.example.animecompose.data.vos.MovieVO
import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("dates")
    val dates: DateVO?,

    @SerializedName("results")
    val results: List<MovieVO>?
)