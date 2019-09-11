package com.github.bkhezry.fastadaptertest.util

import com.github.bkhezry.fastadaptertest.model.ResponseEarthquake
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("all_hour.geojson")
    fun getHourlyEarthquake(): Single<ResponseEarthquake>
}