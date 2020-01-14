package com.cmpayne.dnd5e.models

import com.cmpayne.dnd5e.Utilities

class Encounter(
    val monsters: MutableList<Monster>,
    val monsterCount: MutableList<Int>,
    val party: Party
) {
    val experience: Int
        get() {
            var exp = 0
            monsters.forEachIndexed { index, monster ->
                exp += (monster.challenge.first().getXp() * monsterCount[index])
            }
            return exp
        }

    val multiplier: Int
        get() {
            var mult = 0
            val monsterTotal = totalMonsters
            if (monsterTotal > 0) mult++
            if (monsterTotal > 1) mult++
            if (monsterTotal > 2) mult++
            if (monsterTotal > 6) mult++
            if (monsterTotal > 10) mult++
            if (monsterTotal > 14) mult++

            when {
                party.size < 1 -> mult++
                party.size in 1..2 -> mult++
                party.size >= 6 -> mult--
            }

            return mult
        }

    val groupCategory: String
        get() {
            return Utilities.groupCats[multiplier]
        }

    val totalMonsters: Int
        get() {
            var numMonsters = 0
            monsterCount.forEach {
                numMonsters += it
            }
            return numMonsters
        }

    val mosnterGroupString = "$totalMonsters $groupCategory"
    //    val xP to award: " + totalXP + " XP" + (characterTotal ? " (" + totalXP / characterTotal + " XP each)" : "") + "</p>";
    val difficultyMultiplier = Utilities.multiplierLookup[multiplier]
    val adjustedDifficultyRating = experience * Utilities.multiplierLookup[multiplier]

    fun getRating(): String {
        return Utilities.getCR((experience * Utilities.multiplierLookup[multiplier]).toInt())
    }

    fun getDifficulty(): String {
        val diff = (experience.toFloat() * multiplier)/party.xp.second

        /*return when {
             diff <= 0 -> {
                return "0"
            }
             diff <= (1f / 8f) -> {
                return "1/8"
            }
             diff <= (1f / 4f) -> {
                return "1/4"
            }
             diff <= (1f / 2f) -> {
                return "1/2"
            }
            else -> {
                return diff.toInt().toString()
            }
        }*/
        return "%.1f".format(diff)
    }

}

class Party(var size: Int = 0, var level: Int = 0) {
    val xp: Triple<Int, Int, Int>
        get() {
            var easyXP = 0
            var mediumXP = 0
            var hardXP = 0
            var deadlyXP = 0
            val l = level - 1
            if (l >= 0 && size > 0) {
                easyXP += Utilities.xpLookup[l][0] * size
                mediumXP += Utilities.xpLookup[l][1] * size
                hardXP += Utilities.xpLookup[l][2] * size
                deadlyXP += Utilities.xpLookup[l][3] * size
            }
            return Triple(easyXP, mediumXP, hardXP)
        }

    val sizeString: String
        get() {
            return when {
                size < 1 -> "(none)"
                size in 1..2 -> "(small party)"
                size >= 6 -> "(large party)"
                else -> "(party)"
            }
        }

    override fun toString() = "${this.size} ${this.sizeString}"
}