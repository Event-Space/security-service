package org.kenuki.securityservice.core.services


import org.kenuki.securitymodule.sessions.SessionMe
import org.kenuki.securitymodule.util.ATTR_USERID
import org.kenuki.securitymodule.util.Roles
import org.kenuki.securityservice.core.repos.UserRepo
import org.kenuki.securityservice.core.utils.JwtGenerator
import org.kenuki.securityservice.web.dtos.request.UpdateProfileDTO
import org.kenuki.securityservice.web.dtos.response.AccessTokenDTO
import org.kenuki.securityservice.web.dtos.response.UserDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val jwtGenerator: JwtGenerator,
    private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder,
) {
    fun getUserProfile(session: SessionMe): ResponseEntity<UserDTO> {
        val userDTO = UserDTO(
            session.getAttribute<String>("email"),
            session.getAttribute<String>("first_name"),
            session.getAttribute<String>("last_name"),
            session.getAttribute<String>("phone_number"),
            session.getAttribute<Roles>("role")?.name,
        )
        return ResponseEntity.ok(userDTO)
    }

    fun updateUserProfile(session: SessionMe, updateProfileDTO: UpdateProfileDTO): Any {
        val userId = session.getAttribute<Long>("user_id") ?: throw ResponseStatusException(
            HttpStatus.FORBIDDEN,
            "No user id in session"
        )

        val user = userRepo.findById(userId).get()

        if (updateProfileDTO.lastName != null) {
            user.lastName = updateProfileDTO.lastName
        }
        if (updateProfileDTO.firstName != null) {
            user.firstName = updateProfileDTO.firstName
        }
        if (updateProfileDTO.password != null) {
            user.password = passwordEncoder.encode(updateProfileDTO.password)
        }
        if (updateProfileDTO.phone != null) {
            user.phoneNumber = updateProfileDTO.phone
        }

        userRepo.save(user)
        return ResponseEntity.ok(AccessTokenDTO(jwtGenerator.generateToken(user)))

    }
}