package com.example.animecompose.network.responses

import com.example.animecompose.data.vos.ActorVO
import com.google.gson.annotations.SerializedName

data class PeopleResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: List<ActorVO>?,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)
