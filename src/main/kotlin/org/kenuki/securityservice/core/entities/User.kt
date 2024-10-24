package org.kenuki.securityservice.core.entities

import jakarta.persistence.*

@Entity
@Table(name = "users", schema = "user_unit")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 64, nullable = false, unique = true)
    var email: String,
    @Column(length = 64)
    var firstName: String? = null,
    @Column(length = 64)
    var lastName: String? = null,
    @Column(length = 80)
    var password: String,
    @Column(length = 15)
    var phoneNumber: String? = null,
    @Enumerated(EnumType.ORDINAL)
    var role: Roles = Roles.USER,

    @OneToMany(mappedBy = "user")
    var refreshTokens: MutableList<RefreshToken> = mutableListOf()
)

enum class Roles {
    USER, MANAGER, ADMIN
}