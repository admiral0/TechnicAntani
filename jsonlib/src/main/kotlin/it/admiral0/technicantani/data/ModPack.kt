package it.admiral0.technicantani.data

import it.admiral0.technicantani.defaultMinecraftVersion
import java.util.*
import kotlin.properties.Delegates

public data class ModPackVersion() {
    var name : String by Delegates.notNull()
    var description : String = "No description"
    var forgever : Optional<String> = Optional.empty()
    var mcversion : MinecraftVersion = defaultMinecraftVersion
    var url : Optional<String> = Optional.empty()
    var version : String = "unknown"
    var mods : Map<String, String> = emptyMap()
}