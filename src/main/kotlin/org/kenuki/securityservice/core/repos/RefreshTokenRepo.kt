package org.kenuki.securityservice.core.repos

import org.kenuki.securityservice.core.entities.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepo : JpaRepository<RefreshToken, String> {
    fun countByExpirationDateBefore(date: Date): Long
    fun deleteAllByExpirationDateBefore(date: Date)
}