package org.kenuki.securityservice.web.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.kenuki.securitymodule.annotations.SecureMe
import org.kenuki.securitymodule.util.Roles
import org.kenuki.securityservice.core.services.ManagerService
import org.kenuki.securityservice.web.dtos.request.CreateUserDTO
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/manager")
@Tag(name = "ManagerController", description = "Here all endpoints require authorization and role MANAGER or ADMIN")
class ManagerController (
    val managerService: ManagerService
) {
    @GetMapping("/users")
    @SecureMe([Roles.MANAGER, Roles.ADMIN])
    fun getAllUsers() = managerService.getAllUsers()

    @PostMapping("/users")
    @SecureMe([Roles.MANAGER, Roles.ADMIN])
    fun registerUser(@RequestBody createUserDTO: CreateUserDTO) = managerService.createUser(createUserDTO)

    @PatchMapping("/users")
    @SecureMe([Roles.MANAGER, Roles.ADMIN])
    @Operation(summary = "Update user", description = "This will only update those fields that were passed in the DTO")
    fun updateUser(@RequestBody createUserDTO: CreateUserDTO, @RequestParam userEmail: String) =
        managerService.updateUser(createUserDTO, userEmail)

    @DeleteMapping("/users")
    @SecureMe([Roles.MANAGER, Roles.ADMIN])
    fun deleteUser(@RequestParam userEmail: String) = managerService.deleteUser(userEmail)
}