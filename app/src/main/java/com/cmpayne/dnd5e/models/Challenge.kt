package com.cmpayne.dnd5e.models

import com.cmpayne.dnd5e.Utilities

class Challenge(val rating: String, val context: String) {
    fun getXp(): Int {
            var output = 0
            val n = rating.split('/')
            if (n.size > 1) {
                val cr = n[0].toFloat() / n[1].toFloat()
                when {
                    cr <= 0 -> {
                        output = 0
                    }
                    cr <= (1f / 8f) -> {
                        output = Utilities.crXPLookup[0]
                    }
                    cr <= (1f / 4f) -> {
                        output = Utilities.crXPLookup[1]
                    }
                    cr <= (1f / 2f) -> {
                        output = Utilities.crXPLookup[2]
                    }
                }
            } else {
                val cr = rating.toInt()
                if (cr > 0) output = Utilities.crXPLookup[cr + 2]
            }
            return output
        }
}
