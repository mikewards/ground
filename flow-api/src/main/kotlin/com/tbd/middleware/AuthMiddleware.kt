package com.tbd.middleware

import com.tbd.model.AccessTokens
import com.tbd.service.TokenService
import com.typesafe.config.ConfigFactory
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.auth() {
    val config = ConfigFactory.load()
    val jwtSecret = System.getenv("JWT_SECRET") ?: config.getString("jwt.secret")
    
    install(Authentication) {
        bearer("bearer-auth") {
            realm = "TBD API"
            authenticate { tokenCredential ->
                try {
                    val tokenService = TokenService()
                    val tokenPrefix = tokenCredential.token.take(8)
                    
                    // First try to validate as a PAT (Personal Access Token) from database
                    val patToken = tokenService.validateToken(tokenCredential.token)
                    if (patToken != null) {
                        println("✅ PAT token validated for account: ${patToken[AccessTokens.accountId]}")
                        return@authenticate UserIdPrincipal(patToken[AccessTokens.accountId].toString())
                    }
                    
                    // Then try to validate as a JWT (from login)
                    try {
                        val key = Keys.hmacShaKeyFor(jwtSecret.toByteArray())
                        val claims = Jwts.parser()
                            .verifyWith(key)
                            .build()
                            .parseSignedClaims(tokenCredential.token)
                        val accountId = claims.payload.subject
                        if (accountId != null) {
                            println("✅ JWT token validated for account: $accountId")
                            return@authenticate UserIdPrincipal(accountId)
                        }
                    } catch (e: Exception) {
                        println("⚠️ JWT validation failed: ${e.message}")
                    }
                    
                    println("❌ Token validation failed - token prefix: $tokenPrefix")
                    null
                } catch (e: Exception) {
                    println("❌ Authentication error: ${e.message}")
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}
