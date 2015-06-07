package it.admiral0.technicantani.data

import kotlin.properties.Delegates

public data class ModPackVersion() {
    var name : String by Delegates.notNull()
    var description : String = "No description"
    var forgever : String? = null
    var mcversion : MinecraftVersion by Delegates.notNull()
    var url : String? = null
    var version : String by Delegates.notNull()
    var mods : Map<String, String> by Delegates.notNull()
}

public data class ModPack() {
    var recommended : String by Delegates.notNull()
    var latest : String by Delegates.notNull()
    var versions : Array<ModPackVersion> by Delegates.notNull()
}