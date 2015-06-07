package it.admiral0.technicantani.validation

import it.admiral0.technicantani.data.Mod
import java.io.File
import java.io.IOException

fun Mod.validate(path : File) {
    if(this.authors.size() == 0)
        throw NoAuthorsException()
    if(this.versions.size() == 0)
        throw NoVersionsException()
    this.versions.forEach {
        if(!File(path.getAbsolutePath() + File.separator + it.getValue().file).exists()){
            throw IOException("Cannot find file " + it.getValue().file)
        }
    }
}