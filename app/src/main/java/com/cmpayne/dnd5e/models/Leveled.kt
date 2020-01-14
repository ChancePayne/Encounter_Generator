package com.cmpayne.dnd5e.models


import com.google.gson.annotations.SerializedName

data class Leveled(
    @SerializedName("slots")
    val slots: Int,
    @SerializedName("spells")
    val spells: List<String>
)