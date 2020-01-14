package com.cmpayne.dnd5e

import com.cmpayne.dnd5e.models.Monster

interface BestiaryListInterface {
    fun monsterSelected(monster: Monster, requestCode: Int)
    fun monsterLongSelected(monster: Monster)
    fun monsterQtyChanged(monster: Monster, qty: Int)
}