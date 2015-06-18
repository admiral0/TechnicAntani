package it.admiral0.technicantani.data

import it.admiral0.technicantani.data.Mod
import java.io.File
import java.io.IOException
import java.util.*

fun Mod.validate(pathToMod : File) : List<String> {
    val errs : MutableList<String> = ArrayList()
    if(this.authors.size() == 0)
        errs.add("Zero authors.")
    if(this.versions.size() == 0)
        errs.add("No declared versions")
    this.versions.forEach {
        val actualFile = File(pathToMod.getAbsolutePath() + File.separator + it.getValue().file)
        if(!actualFile.exists()){
            val error =  "Cannot find " + actualFile.getAbsolutePath() + " , but it was declared in version " + it.getKey()
            errs.add(error)
        }
    }
    return errs.toList()
}

fun ModPackVersion.validate(repo : ModRepo) : List<String>{
    val errs : MutableList<String> = ArrayList()
    if(!name.isPresent() || name.get().isBlank())
        errs.add("Pack Name cannot be blank")
    for((name, version) in mods){
        if(name !in repo.mods.keySet()) {
            errs.add("Mod $name not found in Mod Repo at '${repo.repoPath}'")
            continue
        }
        val mod : Mod = repo.mods[name]!!

        if(version !in mod.versions.keySet())
            errs.add("Mod $name doesn't have version '$version' in Mod Repo at '${repo.repoPath}'")
    }
    return  errs.toList()
}