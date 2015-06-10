package it.admiral0.technicantani.data

import com.github.salomonbrys.kotson.deserialize
import com.github.salomonbrys.kotson.string
import com.github.salomonbrys.kotson.typeToken
import com.google.gson.*
import it.admiral0.technicantani.data.*
import kotlin.javaClass
import java.io.File
import java.lang.reflect.Type

val defaultMinecraftVersion = MinecraftVersion.v1_8

object gson {
    val builder = GsonBuilder()
    init {
        builder.deserialize<PackagingType> { jsonElement, type, jsonDeserializationContext ->
            if(jsonElement !is JsonPrimitive || !jsonElement.isString())
                throw WrongType(type.getTypeName(), "String", Mod.Companion.json)
            try {
                return@deserialize PackagingType.valueOf(jsonElement.string.toUpperCase())
            }catch (e: Exception){
                throw NotAValidOption(jsonElement.string,
                        PackagingType.values().map<PackagingType, String> { it.toString().toLowerCase() }.toTypedArray()
                )
            }
        }
        builder.deserialize<MinecraftVersion> { jsonElement, type, jsonDeserializationContext ->
            if(jsonElement !is JsonPrimitive || !jsonElement.isString())
                throw WrongType(type.getTypeName(), "String", "minecraft version in mod version")
            try {
                return@deserialize MinecraftVersion.values().first { it.repr == jsonElement.string }
            } catch (e : Exception){
                throw NotAValidOption(
                        jsonElement.string,
                        MinecraftVersion.values().map<MinecraftVersion, String> { it.repr }.toTypedArray()
                )
            }
        }
        builder.deserialize<ModType> { jsonElement, type, jsonDeserializationContext ->
            if(jsonElement !is JsonPrimitive || !jsonElement.isString())
                throw WrongType(type.getTypeName(), "String", "mod type in mod version")
            try {
                return@deserialize ModType.valueOf(jsonElement.string.toUpperCase())
            } catch (e : Exception){
                throw NotAValidOption(
                        jsonElement.string,
                        ModType.values().map<ModType, String> { it.toString().toLowerCase() }.toTypedArray()
                )
            }
        }
    }
}