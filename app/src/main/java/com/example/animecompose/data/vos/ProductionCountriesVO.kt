package com.example.animecompose.data.vos

import com.google.gson.annotations.SerializedName

data class ProductionCountriesVO(
    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null
)
