package it.admiral0.technicantani.data

import it.admiral0.technicantani.data.Mod
import java.io.File
import java.io.IOException

fun Mod.validate(path : File) : List<String> {
    val errs : List<String> = emptyList()
    if(this.authors.size() == 0)
        errs + "Zero authors."
    if(this.versions.size() == 0)
        errs + "No declared versions"
    this.versions.forEach {
        val actualFile = File(path.getAbsolutePath() + File.separator + it.getValue().file)
        if(!actualFile.exists()){
            val error =  "Cannot find " + actualFile.getAbsolutePath() + " , but it was declared in version " + it.getKey()
            errs + error
        }
    }
    return errs
}

fun ModPackVersion.validate(repo : ModRepo){

}