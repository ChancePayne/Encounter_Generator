package com.cmpayne.dnd5e.models


import com.google.gson.annotations.SerializedName

data class Spells(
    @SerializedName("0")
    val cantrips: Cantrips,
    @SerializedName("1")
    val level1: Leveled,
    @SerializedName("2")
    val level2: Leveled,
    @SerializedName("3")
    val level3: Leveled,
    @SerializedName("4")
    val level4: Leveled,
    @SerializedName("5")
    val level5: Leveled

)