package org.kenuki.securityservice.core.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.kenuki.securityservice.core.ACCESS_LIFETIME_IN_MS
import org.kenuki.securityservice.core.entities.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {
    @Value("\${jwt.secret}")
    var jwtKey: String = "d"
    val logger: Logger = LoggerFactory.getLogger(JwtUtil::class.java)

    fun generateToken(user: User): String {
        val now = Date()
        val due = Date(now.time + ACCESS_LIFETIME_IN_MS)
        val claims = mapOf(
            "user_id" to user.id,
            "email" to user.email,
            "first_name" to user.firstName,
            "last_name" to user.lastName,
            "phone_number" to user.phoneNumber,
            "role" to user.role,
        )
        return Jwts.builder()
            .issuedAt(now)
            .expiration(due)
            .claims(claims)
            .signWith(key())
            .compact()
    }

    fun extractTokenPayload(token: String): Any? {
        try {
            return Jwts.parser()
                .verifyWith(key())
                .build()
                .parse(token)
                .payload
        } catch (e: Exception) {
            logger.info("Failed to validate token ${e.message}")
            return null
        }
    }

    fun key(): SecretKey {
        return Keys.hmacShaKeyFor(jwtKey.toByteArray())
    }

}