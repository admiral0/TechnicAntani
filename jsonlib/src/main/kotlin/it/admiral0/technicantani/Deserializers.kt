package it.admiral0.technicantani

import com.github.salomonbrys.kotson.deserialize
import com.github.salomonbrys.kotson.typeToken
import kotlin.javaClass
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import it.admiral0.technicantani.data.Mod
import it.admiral0.technicantani.data.ModPack
import java.lang.reflect.Type

object gson {
    val builder =GsonBuilder()
    init {
        builder.deserialize<Mod> { jsonElement, type, jsonDeserializationContext ->
            if(jsonElement !is JsonObject)
                throw WrongType(type, "dict", Mod.json)

            return@deserialize Mod()
        }
    }
}