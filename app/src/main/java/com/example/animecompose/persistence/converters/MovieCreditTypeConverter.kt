package com.example.animecompose.persistence.converters

import androidx.room.TypeConverter
import com.example.animecompose.data.vos.ActorVO
import com.example.animecompose.network.responses.MovieCreditResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.typeOf

class MovieCreditTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromActorList(actorList: List<ActorVO>?) : String? {
        return if(actorList == null)  null else gson.toJson(actorList)
    }

    @TypeConverter
    fun toActorList(actorListString: String?) : List<ActorVO>? {
       return if(actorListString == null) null else gson.fromJson(actorListString, object:  TypeToken<List<ActorVO>>(){}.type)
    }

}