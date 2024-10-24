package org.kenuki.securityservice.web.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.kenuki.securityservice.core.services.AuthService
import org.kenuki.securityservice.web.dtos.request.LoginDTO
import org.kenuki.securityservice.web.dtos.request.RefreshDTO
import org.kenuki.securityservice.web.dtos.request.RegisterDTO
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthController", description = "Here user can login, register and refresh tokens")
class AuthController (
    val authService: AuthService,
){
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user, some field are not necessary")
    fun register(@RequestBody registerDTO: RegisterDTO) = authService.register(registerDTO)

    @PostMapping("/login")
    @Operation(summary = "Login a new user", description = "Login a new user, field \"emailOrUsername\" can be replaced to \"username\" or \"email\"")
    fun login(@RequestBody loginDTO: LoginDTO) = authService.login(loginDTO)

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Refreshes access and refresh tokens")
    fun refresh(@RequestBody refreshDTO: RefreshDTO) = authService.refresh(refreshDTO)
}