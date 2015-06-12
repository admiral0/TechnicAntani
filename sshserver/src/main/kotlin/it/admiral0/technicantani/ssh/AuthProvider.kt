package it.admiral0.technicantani.ssh

import io.vertx.core
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.AbstractUser
import io.vertx.ext.auth.AuthProvider
import io.vertx.ext.auth.User
import org.apache.sshd.server.PublickeyAuthenticator
import org.apache.sshd.server.session.ServerSession
import java.security.PublicKey
import java.util.*
import kotlin.properties.Delegates

class User(val username : String, val permissions: List<String>) : AbstractUser() {
    var auth : AuthProvider by Delegates.notNull()
    override fun doIsPermitted(permission: String, resultHandler: Handler<AsyncResult<Boolean>>) {
        resultHandler.handle(object : AsyncResult<Boolean>{
            override fun cause(): Throwable? {
                return null
            }
            override fun failed(): Boolean {
                return false
            }
            override fun succeeded(): Boolean {
                return true
            }
            override fun result(): Boolean? {
                return permission in this@User.permissions
            }
        })
    }

    override fun setAuthProvider(authProvider: AuthProvider) {
        auth = authProvider
    }

    override fun principal(): JsonObject {
        val obj = JsonObject()
        obj.put("username", username)
        return obj
    }
}

class PubkeyAuthProvider(val vertx: Vertx) : AuthProvider, PublickeyAuthenticator {
    override fun authenticate(authInfo: JsonObject?, resultHandler: Handler<AsyncResult<User>>?) {
        throw UnsupportedOperationException()
    }

    override fun authenticate(username: String?, key: PublicKey?, session: ServerSession?): Boolean {
        throw UnsupportedOperationException()
    }
}

