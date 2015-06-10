package it.tellnet.technicantani

import it.admiral0.technicantani.data.Mod
import it.admiral0.technicantani.data.ModRepo
import it.admiral0.technicantani.data.validate
import org.junit.Test as test
import org.junit.Assert as junit
import java.io.File
import kotlin.io.createTempDir
import kotlin.test.assertEquals

class TestModRepo {
    test fun testModRepo(){
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
        val m : Mod = repo.mods.first()
        assertEquals(m.name, "testmod")
        val errs = m.validate(File(dir.getAbsolutePath() + File.separator + "testmod"))
        junit.assertTrue(errs.contains("Zero authors."))
        junit.assertTrue(errs.contains("No declared versions"))
    }

}
