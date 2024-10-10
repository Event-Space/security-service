package org.kenuki.securityservice.core.entities

import jakarta.persistence.*
import java.util.*

@Entity
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val refreshToken: String? = null,
    @ManyToOne
    val user: User,
    val issueDate: Date,
    val expirationDate: Date,
){
    fun isExpired(): Boolean {
        return expirationDate.before(Date())
    }
}
