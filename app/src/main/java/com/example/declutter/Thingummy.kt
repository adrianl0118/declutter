package com.example.declutter

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Thingummy(
    val name : String? = "",
    val type : String? = "",
    val dim : String? = "",
    val description : String? = "",
    val contact : String? = "",
    val city : String? = "")