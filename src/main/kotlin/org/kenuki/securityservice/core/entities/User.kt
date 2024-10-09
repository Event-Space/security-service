package org.kenuki.securityservice.core.entities

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 64, nullable = false, unique = true)
    var username: String,
    @Column(length = 64, nullable = false, unique = true)
    var email: String,
    @Column(length = 64, nullable = false)
    var firstName: String,
    @Column(length = 64, nullable = true)
    var lastName: String? = null,
    @Column(length = 80, nullable = true)
    var password: String,
    @Column(length = 15, nullable = false, unique = true)
    var phoneNumber: String?,
    @Enumerated(EnumType.ORDINAL)
    var role: Roles = Roles.USER,
)

enum class Roles {
    USER, MANAGER, ADMIN
}