package com.jchc.practica2.data.remote.model

import com.google.gson.annotations.SerializedName


data class OrgDetailDto(
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("long_desc")
    val longDesc: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("tricode")
    val tricode: String? = null,
    @SerializedName("val_coach")
    val valCoach: String? = null,
    @SerializedName("val_roster")
    val valRoster: List<ValRoster>? = null,
    @SerializedName("ytvideo")
    val ytvideo: String? = null,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("long")
    val long: Double? = null,
)

data class ValRoster(
    @SerializedName("2")
    val player2: String? = null,
    @SerializedName("3")
    val player3: String? = null,
    @SerializedName("4")
    val player4: String? = null,
    @SerializedName("5")
    val player5: String? = null,
    @SerializedName("IGL")
    val igl: String? = null
)
