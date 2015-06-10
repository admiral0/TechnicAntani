package it.tellnet.technicantani.jsonlib.util

val validModInfo = """
        {
            "authors" : ["author1", "author2"],
            "description" : "Some description",
            "url" : "asdomare",
            "type" : "prepackaged",
            "versions": {
                "1.0" : {
                    "file" : "test",
                    "minecraft": ["1.7.10"],
                    "type": "client"
                }
            }
        }
        """
val emptyMod = """
{}
"""

val invalidModpackMissingMod = """
   {
        "description": "Back To Antani Modpack",
        "forgever": "10.13.3.1428",
        "mcversion": "1.7.10",
        "version" : "0.3.0",
        "mods": {
            "missingmod" : "NOPE"
        }
    }
"""

val invalidModpackMissingVersion = """
   {
        "description": "Back To Antani Modpack",
        "forgever": "10.13.3.1428",
        "mcversion": "1.7.10",
        "mods": {
            "testmod" : "missingversion"
        },
        "version" : "0.3.0"
    }
"""