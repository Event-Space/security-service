package org.kenuki.securityservice.core.services

import org.kenuki.securityservice.core.entities.Roles
import org.kenuki.securityservice.core.entities.User
import org.kenuki.securityservice.core.repos.RefreshTokenRepo
import org.kenuki.securityservice.core.repos.UserRepo
import org.kenuki.securityservice.core.utils.JwtUtil
import org.kenuki.securityservice.core.utils.RefreshUtil
import org.kenuki.securityservice.web.dtos.request.LoginDTO
import org.kenuki.securityservice.web.dtos.request.RefreshDTO
import org.kenuki.securityservice.web.dtos.request.RegisterDTO
import org.kenuki.securityservice.web.dtos.response.TokenDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
    val userRepo: UserRepo,
    val passwordEncoder: PasswordEncoder,
    val jwtUtil: JwtUtil,
    val refreshUtil: RefreshUtil,
    val refreshTokenRepo: RefreshTokenRepo,
) {
    val logger: Logger = LoggerFactory.getLogger(AuthService::class.java)

    fun register(registerDTO: RegisterDTO): ResponseEntity<Any> {
        validateNewUser(registerDTO)

        registerDTO.apply {
            password = passwordEncoder.encode(password)
        }

        val newUser = User(
            email = registerDTO.email,
            firstName = registerDTO.firstName,
            lastName = registerDTO.lastName,
            password = registerDTO.password,
            phoneNumber = registerDTO.phone,
            role = Roles.USER
        )

        userRepo.save(newUser)
        logger.info("Successfully registered new user {}", newUser)
        return ResponseEntity(HttpStatus.CREATED)
    }

    fun login(loginDTO: LoginDTO): ResponseEntity<TokenDTO> {
        val user: User = loginDTO.run {
            userRepo.findByEmail(emailOrUsername)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

        if (!passwordEncoder.matches(loginDTO.password, user.password))
            return ResponseEntity(HttpStatus.UNAUTHORIZED)

        val accessToken = jwtUtil.generateToken(user)
        val refreshToken = refreshUtil.generateRefreshToken(user).refreshToken!!

        return ResponseEntity.ok(TokenDTO(refreshToken, accessToken))
    }

    fun refresh(refreshDTO: RefreshDTO): ResponseEntity<TokenDTO> {
        val refreshToken = refreshTokenRepo.findById(refreshDTO.refreshToken)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

        val newRefreshToken = refreshUtil.rotateRefreshToken(refreshToken)
        val newAccessToken = jwtUtil.generateToken(refreshToken.user)

        return ResponseEntity.ok(TokenDTO(newRefreshToken, newAccessToken))
    }

    private fun validateNewUser(registerDTO: RegisterDTO) {
        if (userRepo.existsByEmail(registerDTO.email))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!")
        registerDTO.phone?.run {
            if (userRepo.existsByPhoneNumber(this))
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists!")
        }
    }

}