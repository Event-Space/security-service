package org.kenuki.securityservice.web.dtos.request

data class RegisterDTO(
    val phone: String?,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String?,
    var password: String
)
