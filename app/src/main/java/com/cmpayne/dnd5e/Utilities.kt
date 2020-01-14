package com.cmpayne.dnd5e

object Utilities {
    val xpLookup = arrayOf(
        arrayOf(25, 50, 75, 100),
        arrayOf(50, 100, 150, 200),
        arrayOf(75, 150, 225, 400),
        arrayOf(125, 250, 375, 500),
        arrayOf(250, 500, 750, 1100),
        arrayOf(300, 600, 900, 1400),
        arrayOf(350, 750, 1100, 1700),
        arrayOf(450, 900, 1400, 2100),
        arrayOf(550, 1100, 1600, 2400),
        arrayOf(600, 1200, 1900, 2800),
        arrayOf(800, 1600, 2400, 3600),
        arrayOf(1000, 2000, 3000, 4500),
        arrayOf(1100, 2200, 3400, 5100),
        arrayOf(1250, 2500, 3800, 5700),
        arrayOf(1400, 2800, 4300, 6400),
        arrayOf(1600, 3200, 4800, 7200),
        arrayOf(2000, 3900, 5900, 8800),
        arrayOf(2100, 4200, 6300, 9500),
        arrayOf(2400, 4900, 7300, 10900),
        arrayOf(2800, 5700, 8500, 12700)
    )

    val groupCats = arrayOf(
        "none",
        "single",
        "pair",
        "group",
        "gang",
        "mob",
        "horde"
    )

    val multiplierLookup = arrayOf<Float>(
        0.5f,
        1f,
        1.5f,
        2f,
        2.5f,
        3f,
        4f,
        5f
    )

    val crLookup = arrayOf(
        "0", "1/8", "1/4", "1/2", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
        "26", "27", "28", "29", "30"
    )
    val crXPLookup = arrayOf(
        25,
        50,
        100,
        200,
        450,
        700,
        1100,
        1800,
        2300,
        2900,
        3900,
        5000,
        5900,
        7200,
        8400,
        10000,
        11500,
        13000,
        15000,
        18000,
        20000,
        22000,
        25000,
        33000,
        41000,
        50000,
        62000,
        75000,
        90000,
        105000,
        120000,
        135000,
        155000
    )

    fun parseRatio(ratio: String): Double {
        if (ratio.contains("/")) {
            val rat = ratio.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return java.lang.Double.parseDouble(rat[0]) / java.lang.Double.parseDouble(rat[1])
        } else {
            return java.lang.Double.parseDouble(ratio)
        }
    }

    fun getCR(xp: Int): String {
        var cr = 0
        crXPLookup.forEach { if (it <= xp) cr++ }

        return crLookup[cr]
    }

    fun decimalToRational(d: Double): String {
        val ds = d.toString().trimEnd('0').trimEnd('.')
        val index = ds.indexOf('.')
        if (index == -1) return "${ds.toLong()}/${1L}"
        var num = ds.replace(".", "").toLong()
        var den = 1L
        for (n in 1..(ds.length - index - 1)) den *= 10L
        while (num % 2L == 0L && den % 2L == 0L) {
            num /= 2L
            den /= 2L
        }
        while (num % 5L == 0L && den % 5L == 0L) {
            num /= 5L
            den /= 5L
        }
        return "$num/$den"
    }
}