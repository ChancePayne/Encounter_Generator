package com.cmpayne.dnd5e.models

import android.util.Log
import com.google.gson.*

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Monsters (
	/*@SerializedName("monster")*/ val monsters : List<Monster>
)

class MonstersDeserilizer : JsonDeserializer<Monsters> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: java.lang.reflect.Type,
        context: JsonDeserializationContext
    ): Monsters {
//        val monsters = Gson().fromJson<com.cmpayne.dnd5e.models.Monsters>(json, com.cmpayne.dnd5e.models.Monsters::class.java)
        val monsterList = mutableListOf<Monster>()
        val jsonObject = json.asJsonObject

        if (jsonObject.has("monster")) {
            val list = jsonObject.getAsJsonArray("monster")
            if (list != null && !list.isJsonNull) {
                val gsonDeserilizer = GsonBuilder()
                    .registerTypeAdapter(Monster::class.java, MonsterDeserializer())
                    .create()
                jsonObject.get("monster").asJsonArray.forEachIndexed { index, it ->
                    Log.d("MonstersDeserilizer", "$index, ${it.toString()}")

                    monsterList.add(gsonDeserilizer.fromJson(it, Monster::class.java))
                }
            }
        }
        return Monsters(monsterList)
    }
}