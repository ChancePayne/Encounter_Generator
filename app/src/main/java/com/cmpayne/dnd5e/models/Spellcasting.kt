package com.cmpayne.dnd5e.models


import com.google.gson.annotations.SerializedName

data class Spellcasting(
    @SerializedName("ability")
    val ability: String,
    @SerializedName("headerEntries")
    val headerEntries: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("spells")
    val spells: Spells
)