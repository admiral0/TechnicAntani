package it.admiral0.technicantani.data

import com.github.salomonbrys.kotson.typeToken
import it.admiral0.technicantani.data.defaultMinecraftVersion
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.Ref
import java.io.File
import java.util.*
import kotlin.properties.Delegates

public class ModPackVersion() {
    companion object {
        val json = "modpack.json"
    }

    var name : Optional<String> = Optional.empty()
    var description : String = "No description"
    var forgever : Optional<String> = Optional.empty()
    var mcversion : MinecraftVersion = defaultMinecraftVersion
    var url : Optional<String> = Optional.empty()
    var version : Optional<String> = Optional.empty()
    var mods : Map<String, String> = emptyMap()
}

public class ModPack(val path : String) {
    var recommended : Optional<String> = Optional.empty()
    var latest : Optional<String> = Optional.empty()
    var versions : Map<String, ModPackVersion> = emptyMap()

    init {
        val fileRepository = FileRepository(path + "/.git")
        val git = Git(fileRepository)
        val metafile = File(path + File.separator + ModPackVersion.json)
        if(git.branchList().call().filter { it.getName()=="/refs/heads/stable" }.size() != 1){
            throw  MissingBranch("stable", git)
        }
        git.checkout().setName("stable").setForce(true).call()
        val stable : ModPackVersion = gson.builder.create().fromJson(metafile.readText(Charsets.UTF_8), typeToken<ModPackVersion>())
        git.checkout().setName("master").setForce(true).call()
        val master : ModPackVersion = gson.builder.create().fromJson(metafile.readText(Charsets.UTF_8), typeToken<ModPackVersion>())
        recommended = stable.version
        latest = master.version
        val vers : MutableMap<String, ModPackVersion> = HashMap()
        for(version in git.tagList().call().map { it.getName().replace("refs/tags/","") }){
            git.checkout().setName(version).setForce(true).call()
            vers.put(
                    version,
                    gson.builder.create().fromJson(metafile.readText(Charsets.UTF_8), typeToken<ModPackVersion>())
            )
        }
        versions = vers
    }
    
}