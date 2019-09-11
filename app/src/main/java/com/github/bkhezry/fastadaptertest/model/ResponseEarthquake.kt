package com.github.bkhezry.fastadaptertest.model

import com.google.gson.annotations.SerializedName

data class ResponseEarthquake(
    @SerializedName("type")
    val type: String,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("bbox")
    val bbox: List<Double>
)