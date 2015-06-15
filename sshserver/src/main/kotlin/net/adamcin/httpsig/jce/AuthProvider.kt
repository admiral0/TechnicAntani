package net.adamcin.httpsig.jce

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.AbstractUser
import io.vertx.ext.auth.AuthProvider
import io.vertx.ext.auth.User
import it.admiral0.technicantani.ssh
import net.adamcin.httpsig.api.Base64
import net.adamcin.httpsig.jce.AuthorizedKeys
import org.apache.sshd.server.PublickeyAuthenticator
import org.apache.sshd.server.session.ServerSession
import java.io.File
import java.security.PublicKey
import kotlin.properties.Delegates
import kotlin.text.Regex

class AuthException() : Exception()


class AntaniUser(val username : String, val permissions: List<String>) : AbstractUser() {
    var auth : AuthProvider by Delegates.notNull()
    override fun doIsPermitted(permission: String, resultHandler: Handler<AsyncResult<Boolean>>) {
        resultHandler.handle(object : AsyncResult<Boolean> {
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
                return permission in this@AntaniUser.permissions
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

public class PubkeyAuthProvider(val vertx: Vertx) : AuthProvider, PublickeyAuthenticator {
    var userCache : LoadingCache<String, AntaniUser> = CacheBuilder.newBuilder().maximumSize(10).build(object : CacheLoader<String, it.admiral0.technicantani.ssh.User>(){
        override fun load(key: String): AntaniUser {
            val path = vertx.getOrCreateContext().config().getString("authStore")
            val perms = File(path + File.separator + key)
            if(!perms.exists() || !perms.canRead() )
                throw AuthException()
            return AntaniUser(
                    key,
                    perms.readLines(Charsets.UTF_8)
            )
        }
    })
    val authorizedKeys : List<AuthorizedKeys.AuthorizedKey> by Delegates.lazy {
        val path = vertx.getOrCreateContext().config().getString("pubKeyStore")
        val pattern = Regex("^([^\\s]+)\\s+([^\\s]+)(\\s+([^\\s].*)|$)")
        return@lazy File(path).readLines(Charsets.UTF_8).filter { it.matches(pattern) }
                .map {
                    return@map AuthorizedKeys.parseAuthorizedKey(it)
                }
    }
    var authKeyCache : LoadingCache<PublicKey, String?> = CacheBuilder.newBuilder().maximumSize(50).build(object : CacheLoader<PublicKey, String?>(){
        override fun load(key: PublicKey): String? {
            for(auth in authorizedKeys){
                if(key.getEncoded() == auth.getEncodedKey().toByteArray(Charsets.UTF_8)){
                    return auth.getComment().splitBy("|", limit = 2)[0]
                }
            }
            return null
        }
    })
    override fun authenticate(authInfo: JsonObject, resultHandler: Handler<AsyncResult<User>>) {
        val user = authInfo.getString("user")
    }

    override fun authenticate(username: String, key: PublicKey, session: ServerSession): Boolean {
        throw UnsupportedOperationException()
    }
}

