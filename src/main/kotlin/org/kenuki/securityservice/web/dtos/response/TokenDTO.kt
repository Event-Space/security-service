package org.kenuki.securityservice.web.dtos.response

data class TokenDTO(
    val refreshToken: String,
    val accessToken: String
)
