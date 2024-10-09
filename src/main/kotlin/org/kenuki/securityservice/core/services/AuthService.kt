package org.kenuki.securityservice.core.services

import org.kenuki.securityservice.core.entities.User
import org.kenuki.securityservice.core.repos.UserRepo
import org.kenuki.securityservice.core.utils.JwtUtil
import org.kenuki.securityservice.web.dtos.request.LoginDTO
import org.kenuki.securityservice.web.dtos.request.RegisterDTO
import org.kenuki.securityservice.web.dtos.response.TokenDTO
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
    val jwtUtil: JwtUtil
) {
    val logger = LoggerFactory.getLogger(AuthService::class.java)

    fun register(registerDTO: RegisterDTO): ResponseEntity<Any> {
        validateNewUser(registerDTO)

        registerDTO.apply {
            password = passwordEncoder.encode(password)
        }

        val newUser = User(
            username = registerDTO.username,
            email = registerDTO.email,
            password = registerDTO.password,
            firstName = registerDTO.firstName,
            phoneNumber = registerDTO.phone,
        )

        userRepo.save(newUser);
        logger.info("Successfully registered new user {}", newUser);
        return ResponseEntity(HttpStatus.CREATED)
    }

    fun login(loginDTO: LoginDTO): ResponseEntity<TokenDTO> {
        val user: User = loginDTO.run {
            when {
                email.isNullOrBlank() && username.isNullOrBlank() -> throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email or Username is required"
                )

                !email.isNullOrBlank() -> userRepo.findByEmail(email)
                else -> userRepo.findByUsername(username!!)
            }
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

        val accessToken = jwtUtil.generateToken(user);

        return if (passwordEncoder.matches(loginDTO.password, user.password))
            ResponseEntity.ok(TokenDTO("test-refresh", accessToken))
        else
            ResponseEntity(HttpStatus.UNAUTHORIZED)
    }

    private fun validateNewUser(registerDTO: RegisterDTO) {
        if (userRepo.existsByEmail(registerDTO.email))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!")
        if (userRepo.existsByUsername(registerDTO.username))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!")
        registerDTO.phone?.run {
            if (userRepo.existsByPhoneNumber(this))
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists!")
        }
    }
}