package com.cmpayne.dnd5e.models


import com.google.gson.annotations.SerializedName

data class Cantrips(
    @SerializedName("spells")
    val spells: List<String>
)