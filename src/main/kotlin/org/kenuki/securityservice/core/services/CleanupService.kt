package org.kenuki.securityservice.core.services

import org.kenuki.securityservice.core.repos.RefreshTokenRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class CleanupService(
    val tokenRepo: RefreshTokenRepo
) {
    private val logger: Logger = LoggerFactory.getLogger(CleanupService::class.java)

    @Scheduled(cron = "0 0 0 1 * *")
    fun cleanRefreshTokenTable() {
        logger.info("Cleaning up refresh token table!")
        val now = Date()
        val expiredTokenAmount = tokenRepo.countByExpirationDateBefore(now)
        tokenRepo.deleteAllByExpirationDateBefore(now)
        logger.info("Deleted $expiredTokenAmount expired refresh-tokens")
    }
}