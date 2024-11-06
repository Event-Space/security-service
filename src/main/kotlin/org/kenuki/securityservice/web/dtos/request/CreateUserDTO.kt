package org.kenuki.securityservice.web.dtos.request

import org.kenuki.securitymodule.util.Roles

data class CreateUserDTO(
    val phone: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val password: String?,
    val role: Roles?
)