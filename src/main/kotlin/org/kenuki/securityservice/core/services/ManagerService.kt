package org.kenuki.securityservice.core.services

import org.kenuki.securitymodule.util.Roles
import org.kenuki.securityservice.core.entities.User
import org.kenuki.securityservice.core.repos.UserRepo
import org.kenuki.securityservice.web.dtos.request.CreateUserDTO
import org.kenuki.securityservice.web.dtos.request.RegisterDTO
import org.kenuki.securityservice.web.dtos.response.UserDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class ManagerService (
    val userRepo: UserRepo,
    val passwordEncoder: PasswordEncoder,
) {
    fun getAllUsers() =
        userRepo.findAll().map { UserDTO(it.email, it.firstName, it.lastName, it.phoneNumber, it.role.name) }

    @Transactional
    fun createUser(createUserDTO: CreateUserDTO): ResponseEntity<Any> {
        val user = User(
            email = createUserDTO.email ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required"),
            firstName = createUserDTO.firstName,
            lastName = createUserDTO.lastName,
            password = createUserDTO.password
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required"),
            phoneNumber = createUserDTO.phone,
            role = createUserDTO.role ?: Roles.USER
        )
        user.password = passwordEncoder.encode(createUserDTO.password)
        userRepo.save(user)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @Transactional
    fun updateUser(createUserDTO: CreateUserDTO, userEmail: String): ResponseEntity<Any> {
        val user = userRepo.findByEmail(userEmail) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email $userEmail does not exist")
        if(createUserDTO.email != null)
            user.email = createUserDTO.email
        if(createUserDTO.firstName != null)
            user.firstName = createUserDTO.firstName
        if(createUserDTO.lastName != null)
            user.lastName = createUserDTO.lastName
        if(createUserDTO.phone != null)
            user.phoneNumber = createUserDTO.phone
        if(createUserDTO.role != null)
            user.role = createUserDTO.role
        if(createUserDTO.password != null)
            user.password = passwordEncoder.encode(createUserDTO.password)
        userRepo.save(user)
        return ResponseEntity(HttpStatus.ACCEPTED)
    }

    @Transactional
    fun deleteUser(userEmail: String): ResponseEntity<Any> {
        val user = userRepo.findByEmail(userEmail) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email $userEmail does not exist")
        userRepo.deleteUserById(user.id!!);
        return ResponseEntity(HttpStatus.OK)
    }
}