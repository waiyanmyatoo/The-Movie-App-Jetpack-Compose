package com.example.animecompose.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ActorVO(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("gender") var gender: Int? = null,
    @SerializedName("id") @PrimaryKey  var id: Int? = null,
    @SerializedName("known_for_department") var knownForDepartment: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("original_name") var originalName: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("profile_path") var profilePath: String? = null,
    @SerializedName("known_for") var knownFor: List<KnownForVO>?
)
