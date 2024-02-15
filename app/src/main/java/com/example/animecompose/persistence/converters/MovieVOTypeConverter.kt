package com.example.animecompose.persistence.converters

import androidx.room.TypeConverter
import com.example.animecompose.data.vos.GenresVO
import com.example.animecompose.data.vos.ProductionCompaniesVO
import com.example.animecompose.data.vos.ProductionCountriesVO
import com.example.animecompose.data.vos.SpokenLanguagesVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieVOTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromGenresVOList(genresVO: List<GenresVO>): String? {
        return if(genresVO == null) null else gson.toJson(genresVO)
    }

    @TypeConverter
    fun toGenresVOList(genresVOString: String?): List<GenresVO>? {
        return if(genresVOString == null) null else gson.fromJson(genresVOString, object : TypeToken<List<GenresVO>>() {}.type)
    }

    @TypeConverter
    fun fromProductionCompaniesVOList(productionCompaniesVO: List<ProductionCompaniesVO>): String? {
        return if(productionCompaniesVO == null) null else gson.toJson(productionCompaniesVO)
    }

    @TypeConverter
    fun toProductionCompaniesVOList(productionCompaniesVO: String?): List<ProductionCompaniesVO>? {
        return if(productionCompaniesVO == null) null else gson.fromJson(productionCompaniesVO, object : TypeToken<List<ProductionCompaniesVO>>() {}.type)
    }

    @TypeConverter
    fun fromSpokenLanguagesVOList(spokenLanguage: List<SpokenLanguagesVO>): String? {
        return if(spokenLanguage == null) null else gson.toJson(spokenLanguage)
    }

    @TypeConverter
    fun toSpokenLanguagesVOList(spokenLanguage: String?): List<SpokenLanguagesVO>? {
        return if(spokenLanguage == null) null else gson.fromJson(spokenLanguage, object : TypeToken<List<SpokenLanguagesVO>>() {}.type)
    }


    @TypeConverter
    fun fromProductionCountriesVOList(productionCountries: List<ProductionCountriesVO>): String? {
        return if(productionCountries == null) null else gson.toJson(productionCountries)
    }

    @TypeConverter
    fun toProductionCountriesVOList(productionCountries: String?): List<ProductionCountriesVO>? {
        return if(productionCountries == null) null else gson.fromJson(productionCountries, object : TypeToken<List<ProductionCountriesVO>>() {}.type)
    }

    @TypeConverter
    fun fromGenresIdList(genresIdList: List<Int>?): String? {
        return if(genresIdList == null) null else gson.toJson(genresIdList)
    }

    @TypeConverter
    fun toGenresIdList(genresIdList: String?): List<Int>? {
        return if(genresIdList == null) null else gson.fromJson(genresIdList, object : TypeToken<List<ProductionCountriesVO>>() {}.type)
    }
}