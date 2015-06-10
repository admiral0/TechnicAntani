package it.admiral0.technicantani.data

import com.github.salomonbrys.kotson.typeToken
import it.admiral0.technicantani.data.MissingProperty
import it.admiral0.technicantani.data.defaultMinecraftVersion
import java.io.File
import java.util.*
import it.admiral0.technicantani.data.gson
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
    private inner class ModRepoData {
        var authors : Array<String> = emptyArray()
    }
    companion object {
        val json = "meta.json"

        public fun build(repoPath : String) : ModRepo {
            return ModRepo(repoPath)
        }
    }

    val authors : Array<String> by Delegates.lazy {
        val map : ModRepoData =  gson.builder.create().fromJson(
                File(repoPath + File.separator + ModRepo.json).readText(Charsets.UTF_8),
                typeToken<ModRepoData>())
        return@lazy map.authors
    }

    val mods : List<Mod> by Delegates.lazy {
        var root = File(repoPath)
        var mods = ArrayList<Mod>()
        root.listFiles().filter { it.isDirectory() && it.list().contains(Mod.json) }.forEach {
            val mod : Mod = gson.builder.create()
                    .fromJson(File(it.getAbsolutePath() + File.separator + Mod.json)
                            .readText(Charsets.UTF_8), javaClass<Mod>() )
            mod.name = it.getName()
            mods.add(mod)
        }
        return@lazy mods
    }
}

public data class ModVersion() {
    var file : String = ""
    var minecraft : Array<MinecraftVersion> = arrayOf(defaultMinecraftVersion)
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