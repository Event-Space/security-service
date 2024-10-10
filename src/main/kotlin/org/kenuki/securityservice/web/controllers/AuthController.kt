package org.kenuki.securityservice.web.controllers

import org.kenuki.securityservice.core.services.AuthService
import org.kenuki.securityservice.web.dtos.request.LoginDTO
import org.kenuki.securityservice.web.dtos.request.RefreshDTO
import org.kenuki.securityservice.web.dtos.request.RegisterDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController (
    val authService: AuthService,
){
    @PostMapping("/register")
    fun register(@RequestBody registerDTO: RegisterDTO) = authService.register(registerDTO)

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO) = authService.login(loginDTO)

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshDTO: RefreshDTO) = authService.refresh(refreshDTO)
}