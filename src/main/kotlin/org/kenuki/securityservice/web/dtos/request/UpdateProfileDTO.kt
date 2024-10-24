package org.kenuki.securityservice.web.dtos.request

data class UpdateProfileDTO(
    val phone: String?,
    val firstName: String?,
    val lastName: String?,
    var password: String?
)
