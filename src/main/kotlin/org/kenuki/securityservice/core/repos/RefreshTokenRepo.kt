package org.kenuki.securityservice.core.repos

import org.kenuki.securityservice.core.entities.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepo : JpaRepository<RefreshToken, String>