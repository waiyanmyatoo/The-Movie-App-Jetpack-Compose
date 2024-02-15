package com.example.animecompose.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieVO(
    @SerializedName("adult")
    var adult: Boolean?,

    @SerializedName("backdrop_path")
    var backdropPath: String?,

    @SerializedName("genre_ids")
    var genreIds: List<Int>?,

    @SerializedName("id")
    @PrimaryKey
    var id: Int? = null,

    @SerializedName("original_language")
    var originalLanguage: String?,

    @SerializedName("original_title")
    var originalTitle: String?,

    @SerializedName("overview")
    var overview: String?,

    @SerializedName("popularity")
    var popularity: Double?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("release_date")
    var releaseDate: String?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("video")
    var video: Boolean?,

    @SerializedName("vote_average")
    var voteAverage: Double?,

    @SerializedName("vote_count")
    var voteCount: Int?,


//    @SerializedName("belongs_to_collection") var belongsToCollection: String? = null,
    @SerializedName("budget") var budget: Int? = null,
    @SerializedName("genres") var genres: List<GenresVO>?,
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("imdb_id") var imdbId: String? = null,
    @SerializedName("production_companies") var productionCompanies: List<ProductionCompaniesVO>?,
    @SerializedName("production_countries") var productionCountries: List<ProductionCountriesVO>?,

    @SerializedName("revenue") var revenue: Int? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("spoken_languages") var spokenLanguages: List<SpokenLanguagesVO>?,
    @SerializedName("status") var status: String? = null,
    @SerializedName("tagline") var tagline: String? = null,

    ) {
    fun getRatingBasedOnFiveStars(): Float {
        return voteAverage?.div(2)?.toFloat() ?: 0.0f
    }
}
