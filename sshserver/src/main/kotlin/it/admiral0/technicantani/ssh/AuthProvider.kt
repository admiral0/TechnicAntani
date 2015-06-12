package it.admiral0.technicantani.ssh

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import io.vertx.core
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.AbstractUser
import io.vertx.ext.auth.AuthProvider
import io.vertx.ext.auth.User
import it.admiral0.technicantani.ssh
import net.adamcin.httpsig.api.Keychain
import net.adamcin.httpsig.jce.AuthorizedKeys
import org.apache.sshd.server.PublickeyAuthenticator
import org.apache.sshd.server.session.ServerSession
import java.io.File
import java.security.PublicKey
import java.util.*
import java.util.regex.Pattern
import kotlin.properties.Delegates
import kotlin.text.Regex

class AuthException() : Exception()


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
    var userCache : LoadingCache<String, ssh.User> = CacheBuilder.newBuilder().maximumSize(15).build(object : CacheLoader<String,ssh.User>(){
        override fun load(key: String): ssh.User? {
            val path = vertx.getOrCreateContext().config().getString("authStore")
            val perms = File(path + File.separator + key)
            if(!perms.exists() || !perms.canRead() )
                throw AuthException()
            return ssh.User(
                        key,
                        perms.readLines(Charsets.UTF_8)
                    )
        }
    })
    val authorizedKeys : List<Triple<String,String,String>> by Delegates.lazy {
        val path = vertx.getOrCreateContext().config().getString("pubKeyStore")
        val pattern = Regex("^([^\\s]+)\\s+([^\\s]+)(\\s+([^\\s].*)|$)")
        return@lazy File(path).readLines(Charsets.UTF_8).filter { it.matches(pattern) }
                .map {
                    val groups = pattern.match(it).groups
                    return@map Triple(
                            groups.get(1)?.value ?: "" ,
                            groups.get(2)?.value ?: "",
                            groups.get(3)?.value ?: ""
                            )
                }
    }
    var authKeyCache : LoadingCache<PublicKey, String?> = CacheBuilder.newBuilder().maximumSize(50).build(object : CacheLoader<PublicKey,String?>(){
        override fun load(key: PublicKey): String? {
            for(key in authorizedKeys){

            }
        }
    })
    var pubkeyCache : Map<PublicKey,String?> by Delegates.lazy {


    }
    init {

    }
    override fun authenticate(authInfo: JsonObject, resultHandler: Handler<AsyncResult<User>>) {
        throw UnsupportedOperationException()
    }

    override fun authenticate(username: String, key: PublicKey, session: ServerSession): Boolean {
        throw UnsupportedOperationException()
    }
}

