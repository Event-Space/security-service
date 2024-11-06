package org.kenuki.securityservice.core.repos

import org.kenuki.securityservice.core.entities.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface RefreshTokenRepo : JpaRepository<RefreshToken, String> {
    @Transactional(readOnly = true)
    fun countByExpirationDateBefore(date: Date): Long

    fun deleteAllByExpirationDateBefore(date: Date)
}