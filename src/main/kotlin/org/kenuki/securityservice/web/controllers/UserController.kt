package org.kenuki.securityservice.web.controllers

import org.kenuki.securityservice.core.services.UserService
import org.kenuki.securityservice.web.dtos.request.UpdateProfileDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/user")
class UserController (
    val userService: UserService,
) {
    @GetMapping("/profile")
    fun getUserProfile(@RequestHeader("Authorization") authHeader: String?): Any? {
        if (authHeader == null)
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "No Authorization header provided")
        return userService.getUserProfile(authHeader.substringAfter("Bearer "))
    }

    @PostMapping("/profile-update")
    fun updateUserProfile(@RequestHeader("Authorization") authHeader: String?, @RequestBody updateProfileDTO: UpdateProfileDTO): Any {
        if (authHeader == null)
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "No Authorization header provided")
        return userService.updateUserProfile(authHeader.substringAfter("Bearer "), updateProfileDTO)
    }
}