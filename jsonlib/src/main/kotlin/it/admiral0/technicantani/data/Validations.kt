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
    return errs
}

fun ModPackVersion.validate(repo : ModRepo){
    val errs : MutableList<String> = ArrayList()
    if(name.isBlank())
        errs.add("Pack Name cannot be blank")
    errs
}