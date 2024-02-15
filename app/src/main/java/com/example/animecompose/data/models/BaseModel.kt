package com.example.animecompose.data.models

import android.content.Context
import com.example.animecompose.network.TheMovieApi
import com.example.animecompose.persistence.MovieAppDatabase
import com.example.animecompose.utils.BASE_URL
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

abstract class BaseModel {

    protected var mTheMovieApi: TheMovieApi
//    protected var mMovieDatabase: MovieAppDatabase? = null

    init {
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        mTheMovieApi = retrofit.create(TheMovieApi::class.java)

    }

//    fun initDatabase(context: Context){
//        mMovieDatabase = MovieAppDatabase.getInstance(context)
//    }
}