package org.kenuki.securityservice.core.utils

import org.kenuki.securityservice.core.REFRESH_LIFETIME_IN_MS
import org.kenuki.securityservice.core.entities.RefreshToken
import org.kenuki.securityservice.core.entities.User
import org.kenuki.securityservice.core.repos.RefreshTokenRepo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Component
class RefreshUtil(
    val refreshTokenRepo: RefreshTokenRepo
) {
    fun rotateRefreshToken(refreshToken: RefreshToken): String {
        if (refreshToken.isExpired())
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expired refresh token")

        val newRefreshToken = generateRefreshToken(refreshToken.user)
        refreshTokenRepo.delete(refreshToken)
        return newRefreshToken.refreshToken!!
    }

    fun generateRefreshToken(user: User): RefreshToken {
        val now = Date()
        val due = Date(now.time + REFRESH_LIFETIME_IN_MS)
        val newRefreshToken = RefreshToken(
            user = user,
            issueDate = now,
            expirationDate = due,
        )
        return refreshTokenRepo.save(newRefreshToken)
    }
}