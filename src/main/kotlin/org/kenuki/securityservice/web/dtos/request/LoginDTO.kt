package org.kenuki.securityservice.web.dtos.request

data class LoginDTO(
    val email: String?,
    val username: String?,
    val password: String
)
