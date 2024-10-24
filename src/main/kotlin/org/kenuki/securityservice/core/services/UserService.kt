package org.kenuki.securityservice.core.services

import org.apache.http.HttpResponse
import org.kenuki.securityservice.core.entities.User
import org.kenuki.securityservice.core.repos.UserRepo
import org.kenuki.securityservice.core.utils.JwtUtil
import org.kenuki.securityservice.web.dtos.request.UpdateProfileDTO
import org.kenuki.securityservice.web.dtos.response.AccessTokenDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val jwtUtil: JwtUtil,
    private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder,
) {
    fun getUserProfile(token: String): Any? {
        return jwtUtil.extractTokenPayload(token) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Malformed token")
    }
    fun updateUserProfile(token: String, updateProfileDTO: UpdateProfileDTO): Any {
        val payload = (jwtUtil.extractTokenPayload(token) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Malformed token")) as Map<*, *>
        val userId = when (val id = payload["user_id"]) {
            is Int -> id.toLong()
            is Long -> id.toLong()
            else -> throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Malformed token")
        }
        val user = userRepo.findById(userId).get()
        user.lastName = updateProfileDTO.lastName
        user.firstName = updateProfileDTO.firstName
        user.password = passwordEncoder.encode(updateProfileDTO.password)
        user.phoneNumber = updateProfileDTO.phone

        userRepo.save(user)
        return ResponseEntity.ok(AccessTokenDTO(jwtUtil.generateToken(user)));

    }
}