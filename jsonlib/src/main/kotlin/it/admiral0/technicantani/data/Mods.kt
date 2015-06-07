package it.admiral0.technicantani.data

import it.admiral0.technicantani.MissingProperty
import java.io.File
import java.util.*
import it.admiral0.technicantani.gson
import kotlin.properties.Delegates

/**
 * Known minecraft versions
 */
enum class MinecraftVersion(val repr: String){
    v1_7_10("1.7.10"),
    v1_8("1.8")
}

enum class ModType {
    UNIVERSAL,
    SERVER,
    CLIENT
}

enum class PackagingType {
    PREPACKAGED,
    MOD
}

public data class ModRepo private constructor(val repoPath : String) {
    companion object {
        val json = "meta.json"
    }
    var authors : Array<String> by Delegates.notNull()
        private  set
    val mods : List<Mod> by Delegates.lazy {
        var root = File(repoPath)
        var mods = ArrayList<Mod>()
        root.listFiles().filter { it.isDirectory() && it.list().contains(Mod.json) }.forEach {
            val mod : Mod = gson.builder.create().fromJson(it.readText(Charsets.UTF_8), javaClass<Mod>() )
            mods.add(mod)
        }
        return@lazy mods
    }
}

public data class ModVersion() {
    var file : String = ""
    var minecraft : Array<MinecraftVersion> = arrayOf(MinecraftVersion.v1_8)
    var type : ModType = ModType.UNIVERSAL
}

public data class Mod() {
    companion object {
        val json = "mod.json"
    }
    var name : String by Delegates.notNull()
    var authors : Array<String> = emptyArray()
    var description : String = "No description."
    var url : String = ""
    var type : PackagingType = PackagingType.MOD
    var versions : Map<String, ModVersion> = emptyMap()
}