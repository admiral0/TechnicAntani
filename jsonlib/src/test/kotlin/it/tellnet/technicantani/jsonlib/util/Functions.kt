package it.tellnet.technicantani.jsonlib.util

import it.admiral0.technicantani.data.Mod
import it.admiral0.technicantani.data.ModPackVersion
import it.admiral0.technicantani.data.ModRepo
import org.eclipse.jgit.api.Git
import java.io.File

fun createTempRepo(vararg mods : Pair<String,String>) : File {
    val dir = createTempDir()
    val meta = File(dir.getAbsolutePath() + File.separator + ModRepo.json)
    meta.createNewFile()
    meta.writeText("""
        {
            "authors" :  ["antani1", "antani2"]
        }
        """, Charsets.UTF_8)
    for((name, str) in mods){
        val testMod = File(dir.getAbsolutePath() + File.separator +  name + File.separator + Mod.json)
        testMod.getParentFile().mkdirs()
        testMod.createNewFile()
        testMod.writeText(str, Charsets.UTF_8)
    }
    return dir
}