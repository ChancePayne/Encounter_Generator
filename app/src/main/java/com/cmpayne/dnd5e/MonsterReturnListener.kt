package com.cmpayne.dnd5e

import com.cmpayne.dnd5e.models.Monster

interface MonsterReturnListener {
    fun returnMonster(monster: Monster, requestCode: Int)
}
