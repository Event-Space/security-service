package org.kenuki.securityservice.core

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AppConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }
    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .servers(listOf(
                Server().url("https://server.kenuki.org").description("Prod server"),
                Server().url("http://localhost:8080").description("Local server")
            ))
    }
}