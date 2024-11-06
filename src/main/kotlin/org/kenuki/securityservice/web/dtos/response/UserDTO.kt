package org.kenuki.securityservice.web.dtos.response

data class UserDTO(
    val email: String?,
    val firstname: String?,
    val lastname: String?,
    val phoneNumber: String?,
    val role: String?
)
