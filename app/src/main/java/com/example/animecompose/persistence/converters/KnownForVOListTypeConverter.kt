package com.example.animecompose.persistence.converters

import androidx.room.TypeConverter
import com.example.animecompose.data.vos.KnownForVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class KnownForVOListTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromKnownForVOList(knownFor: List<KnownForVO>?): String? {
        return if (knownFor == null) null else gson.toJson(knownFor)
    }

    @TypeConverter
    fun toKnownForVOList(knownForString: String?): List<KnownForVO>? {
        return if (knownForString == null) null else gson.fromJson(knownForString, object : TypeToken<List<KnownForVO>>() {}.type)
    }
}