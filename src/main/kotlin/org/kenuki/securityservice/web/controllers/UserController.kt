package org.kenuki.securityservice.web.controllers

import jakarta.servlet.http.HttpSession
import org.kenuki.securitymodule.annotations.SecureMe
import org.kenuki.securitymodule.sessions.SessionMe
import org.kenuki.securitymodule.util.Roles
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
    @SecureMe
    @GetMapping("/profile")
    fun getUserProfile(session: SessionMe) = userService.getUserProfile(session)

    @SecureMe
    @PostMapping("/profile-update")
    fun updateUserProfile(@RequestBody updateProfileDTO: UpdateProfileDTO) = userService.updateUserProfile(updateProfileDTO)
}