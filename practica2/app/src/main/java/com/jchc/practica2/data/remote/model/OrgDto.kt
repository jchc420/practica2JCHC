package com.jchc.practica2.data.remote.model

import com.google.gson.annotations.SerializedName

data class OrgDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("tricode")
    val tricode: String? = null,
    @SerializedName("val_coach")
    val valCoach: String? = null
)
