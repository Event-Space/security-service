package org.kenuki.securityservice.core.repos

import org.kenuki.securityservice.core.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface UserRepo : JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    fun findByEmail(email: String): User?

    @Transactional(readOnly = true)
    fun existsByEmail(email: String): Boolean

    @Transactional(readOnly = true)
    fun existsByPhoneNumber(phoneNumber: String): Boolean

    fun deleteUserById(userId: Long)

    @Transactional(readOnly = true)
    fun countAllUsers(): Long
}