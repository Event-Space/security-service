package org.kenuki.securityservice.web.dtos.request

import com.fasterxml.jackson.annotation.JsonAlias
import io.swagger.v3.oas.annotations.media.Schema

data class LoginDTO(
    @JsonAlias("username", "email")
    @Schema(description = "Can be replaced by \"username\" or \"email\"")
    val emailOrUsername: String,
    val password: String
)
