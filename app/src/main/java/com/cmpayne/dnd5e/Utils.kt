package com.cmpayne.dnd5e

import java.io.IOException
import java.io.InputStream

object Utils {
    fun inputStreamToString(inputStream: InputStream): String {
        try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            return String(bytes)
        } catch (e: IOException) {
            return ""
        }

    }
}