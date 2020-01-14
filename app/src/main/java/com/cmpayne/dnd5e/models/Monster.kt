package com.cmpayne.dnd5e.models

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Monster(

    @SerializedName("name") val name: String,
    @SerializedName("size") val size: String,
    @SerializedName("source") val source: String,
    @SerializedName("hp") val hp: Hp,
    @SerializedName("str") val str: Int,
    @SerializedName("dex") val dex: Int,
    @SerializedName("con") val con: Int,
    @SerializedName("int") val int: Int,
    @SerializedName("wis") val wis: Int,
    @SerializedName("cha") val cha: Int,
    @SerializedName("passive") val passive: Int,
    @SerializedName("languages") val languages: List<String>,
    @SerializedName("trait") val trait: List<Trait>,
    @SerializedName("page") val page: Int,
    @SerializedName("environment") val environment: List<String>,
    @SerializedName("soundClip") val soundClip: String,
    @SerializedName("languageTags") val languageTags: List<String>,
    @SerializedName("damageTags") val damageTags: List<String>,
    @SerializedName("miscTags") val miscTags: List<String>,
    @SerializedName("otherSources") val otherSources: List<OtherSources>
) {
    /*@SerializedName("typeObj")*/ lateinit var typeObj: Type
    /*@SerializedName("action")*/ lateinit var actionList: MutableList<Action>
    /*@SerializedName("ac")*/ lateinit var armorClass : ArmorClass
    /*@SerializedName("speed")*/ lateinit var speedObj: Speed
    /*@SerializedName("cr")*/ lateinit var challenge: MutableList<Challenge>
    /*@SerializedName("alignment")*/ lateinit var alignmentList: MutableList<Alignment>
    /*@SerializedName("skill")*/ lateinit var skillList: MutableList<Skill>
}

class MonsterDeserializer : JsonDeserializer<Monster> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: java.lang.reflect.Type,
        context: JsonDeserializationContext
    ): Monster {
        val gson = Gson()
        val monster = gson.fromJson(json, Monster::class.java)
        val jsonObject = json.asJsonObject

        // custom type
        typeDeserializer(jsonObject, monster)

        //custom action
        actionDeserializer(monster, jsonObject)

        //custom armor class
        armorClassDeserializer(jsonObject, monster)

        //custom speed
        speedDeserializer(jsonObject, monster)

        //custom challenge rating
        challengeDeserializer(jsonObject, monster)

        //custom alignment
        alignmentDeserialzer(jsonObject, monster)
        
        //custom skill
        skillDeserializer(jsonObject, monster)

        return monster
    }

    private fun skillDeserializer(jsonObject: JsonObject, monster: Monster) {
        val fieldName = "skill"
        monster.skillList = mutableListOf()
        if (jsonObject.has(fieldName)) {
            val skillObj = jsonObject.get(fieldName).asJsonObject
            skillObj.keySet().forEach {
                monster.skillList.add(Skill(it, skillObj.get(it).asString))
            }
        }
    }

    private fun alignmentDeserialzer(jsonObject: JsonObject, monster: Monster) {
        val fieldName = "alignment"
        monster.alignmentList = mutableListOf()
        if (jsonObject.has(fieldName)) {
            if(!jsonObject.get(fieldName).asJsonArray[0].isJsonObject) {
                pullComponents(jsonObject, fieldName, monster)
            } else {
                jsonObject.get(fieldName).asJsonArray.forEach {
                    if (it.asJsonObject.has(fieldName)) {
                        pullComponents(it.asJsonObject, fieldName, monster)
                        /*val components = jsonObject.get(fieldName).asJsonArray
                        monster.alignmentList.add(Alignment(components[0].asString, components[1].asString))*/
                    }

                    if(it.asJsonObject.has("chance")) {
                        monster.alignmentList.last().chance = it.asJsonObject.get("chance").asInt
                    }
                }
            }
        }
    }

    private fun pullComponents(
        jsonObject: JsonObject,
        fieldName: String,
        monster: Monster
    ) {
        val components = jsonObject.get(fieldName).asJsonArray
        if (components.size() < 2) {
            monster.alignmentList.add(
                Alignment(
                    components[0].asString,
                    components[0].asString,
                    100
                )
            )
        } else {
            monster.alignmentList.add(
                Alignment(
                    components[0].asString,
                    components[1].asString,
                    100
                )
            )
        }
    }

    private fun challengeDeserializer(jsonObject: JsonObject, monster: Monster) {
        val fieldName = "cr"
        monster.challenge = mutableListOf()
        if (jsonObject.has(fieldName)) {
            if(!jsonObject.get(fieldName).isJsonObject) {
                monster.challenge.add(Challenge(jsonObject.get(fieldName).asString, ""))
            } else {
                val challengeObj = jsonObject.get(fieldName).asJsonObject

                if (challengeObj.has(fieldName)) {
                    monster.challenge.add(Challenge(challengeObj.get(fieldName).asString, ""))
                }
                if (challengeObj.has("lair")) {
                    monster.challenge.add(Challenge(challengeObj.get("lair").asString, "lair"))
                }
            }
        }
    }

    private fun speedDeserializer(
        jsonObject: JsonObject,
        monster: Monster
    ) {
        val speedFieldName = "speed"
        if (jsonObject.has(speedFieldName)) {
            val speedObj = jsonObject.get(speedFieldName).asJsonObject
            var walk = 0
            var burrow = 0
            var fly = 0
            var swim = 0
            var canHover = false
            var description = ""

            if (speedObj.has("walk")) {
                if (!speedObj.get("walk").isJsonObject) {
                    walk = speedObj.get("walk").asInt
                } else {
                    walk = speedObj.get("walk").asJsonObject.get("number").asInt
                    description = speedObj.get("walk").asJsonObject.get("condition").asString
                }
            }
            if (speedObj.has("burrow")) {
                burrow = speedObj.get("burrow").asInt
            }
            if (speedObj.has("fly")) {
                if (!speedObj.get("fly").isJsonObject) {
                    fly = speedObj.get("fly").asInt
                } else {
                    fly = speedObj.get("fly").asJsonObject.get("number").asInt
                    canHover = speedObj.get("canHover").asBoolean
                }
            }
            if (speedObj.has("swim")) {
                swim = speedObj.get("swim").asInt
            }
            monster.speedObj = Speed(walk, fly, burrow, swim, canHover, description)
        }
    }

    private fun typeDeserializer(
        jsonObject: JsonObject,
        monster: Monster
    ) {
        val typeFieldName = "type"
        if (jsonObject.has(typeFieldName)) {
            val element = jsonObject.get(typeFieldName)
            if (element != null && !element.isJsonNull()) {
                monster.typeObj = if (element.isJsonObject) {

                    val tags = mutableListOf<String>()
                    if(element.asJsonObject.has("tags")) {
                        val tagElements = element.asJsonObject.get("tags").asJsonArray.toList()
                        tagElements.forEachIndexed { index, jsonElement ->
                            tags.add(jsonElement.asString)
                        }
                    }

                    if(element.asJsonObject.has("swarmSize")) {
                        tags.add("Swarm Size ${element.asJsonObject.get("swarmSize").asString}")
                    }

                    Type(element.asJsonObject.get(typeFieldName).asString, tags)
                } else {
                    Type(element.asString, emptyList())
                }
            }
        }
    }

    private fun armorClassDeserializer(
        jsonObject: JsonObject,
        monster: Monster
    ) {
        val acFieldName = "ac"
        if (jsonObject.has(acFieldName)) {
            jsonObject.get(acFieldName).asJsonArray.forEach { element ->
                if (element != null && !element.isJsonNull()) {
                    monster.armorClass = if (element.isJsonObject) {
                        var from = ""

                        if (element.asJsonObject.has("from")) {
                            val fromObj = element.asJsonObject.get("from")
                            from = if (!fromObj.isJsonArray) {
                                fromObj.asString
                            } else {
                                val fromBuilder = StringBuilder()
                                fromObj.asJsonArray.forEach {
                                    fromBuilder.append(it.asString)
                                    fromBuilder.append(" ")
                                }
                                fromBuilder.toString()
                            }
                        }

                        if (element.asJsonObject.has("condition")) {
                            from = element.asJsonObject.get("condition").asString
                        }

                        ArmorClass(
                            element.asJsonObject.get(acFieldName).asInt,
                            from
                        )
                    } else {
                        ArmorClass(element.asInt, "")
                    }
                }
            }
        }
    }

    private fun actionDeserializer(
        monster: Monster,
        jsonObject: JsonObject
    ) {
        monster.actionList = mutableListOf()
        val actionFieldName = "action"
        if (jsonObject.has(actionFieldName)) {
            jsonObject.get(actionFieldName).asJsonArray.forEach { element ->
                if (element != null && !element.isJsonNull()) {
                    val action =
                        Action(element.asJsonObject.get("name").asString, mutableListOf<String>())
                    val entryJson = element.asJsonObject.get("entries").asJsonArray
                    entryJson.forEach { entryElement ->
                        if (!entryElement.isJsonObject) {
                            action.entries.add(entryElement.asString)
                        } else {
                            val items = entryElement.asJsonObject.get("items").asJsonArray
                            if (items != null && !items.isJsonNull()) {
                                items.forEach {
                                    action.subActions.add(
                                        SubAction(
                                            it.asJsonObject.get("name").asString,
                                            it.asJsonObject.get("entry").asString
                                        )
                                    )
                                }
                            }
                        }
                    }
                    monster.actionList.add(action)
                }
            }
        }
    }

}
