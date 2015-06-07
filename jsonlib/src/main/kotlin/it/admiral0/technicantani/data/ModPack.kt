package it.admiral0.technicantani.data

import it.admiral0.technicantani.defaultMinecraftVersion
import kotlin.properties.Delegates

public data class ModPackVersion() {
    var name : String by Delegates.notNull()
    var description : String = "No description"
    var forgever : String? = null
    var mcversion : MinecraftVersion = defaultMinecraftVersion
    var url : String? = null
    var version : String = "unknown"
    var mods : Map<String, String> = emptyMap()
}