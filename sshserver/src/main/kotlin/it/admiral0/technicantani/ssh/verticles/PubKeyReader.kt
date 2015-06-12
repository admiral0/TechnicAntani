package it.admiral0.technicantani.ssh.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import kotlin.properties.Delegates

public class PubKeyReader() : AbstractVerticle() {
    var knownUsers : List<String> = emptyList()
    override fun start() {
        val str = vertx.getOrCreateContext().config().getString("pubKeysDir")
        if(str is String)
            vertx.fileSystem().readDir(str, vertx.me )
    }

    companion object {
        var known
    }
}