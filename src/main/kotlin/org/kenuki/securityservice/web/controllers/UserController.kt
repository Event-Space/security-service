package org.kenuki.securityservice.web.controllers

import org.kenuki.securitymodule.annotations.SecureMe
import org.kenuki.securitymodule.sessions.SessionMe
import org.kenuki.securityservice.core.services.UserService
import org.kenuki.securityservice.web.dtos.request.UpdateProfileDTO
import org.springframework.web.bind.annotation.*

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
    fun updateUserProfile(session: SessionMe, @RequestBody updateProfileDTO: UpdateProfileDTO) = userService.updateUserProfile(session, updateProfileDTO)
}