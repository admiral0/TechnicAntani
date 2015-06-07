package it.tellnet.technicantani

import it.admiral0.technicantani.data.Mod
import it.admiral0.technicantani.data.ModRepo
import org.junit.Test
import org.junit.Assert as junit
import java.io.File
import kotlin.io.createTempDir
import kotlin.test.assertEquals

class TestModRepo {
    @Test
    public fun testModRepo(){
        val dir = createTempDir()
        val meta = File(dir.getAbsolutePath() + File.separator + ModRepo.json)
        meta.createNewFile()
        meta.writeText("""
        {
            "authors" :  ["antani1", "antani2"]
        }
        """, Charsets.UTF_8)
        var repo = ModRepo.build(dir.getAbsolutePath())
        assertEquals(repo.authors.size(),2)
        junit.assertArrayEquals(repo.authors, arrayOf("antani1", "antani2"))
        val testMod = File(dir.getAbsolutePath() + File.separator + "testmod" + File.separator + Mod.json)
        testMod.getParentFile().mkdirs()
        testMod.createNewFile()
        testMod.writeText("""
        {}
        """, Charsets.UTF_8)
        assertEquals(repo.mods.size(),1)
    }

}
