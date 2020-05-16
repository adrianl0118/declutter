package com.example.declutter

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Thingummy(
    val headline : String? = "",
    val type : String? = "",    //i.e. Furniture, electronics, pets etc.
    val width : Int? = 0,         //in cm
    val depth : Int? = 0,
    val height : Int? = 0,
    val weight : Int? = 0,         //in kg
    val desc : String? = "",
    val contact : String? = "",     // Email or phone
    val postalCode : String? = "")   // Canada postal code or America ZIP