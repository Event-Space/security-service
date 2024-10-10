package org.kenuki.securityservice.web

import org.kenuki.securityservice.web.dtos.response.ExceptionDTO
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(e: Exception): ResponseEntity<ExceptionDTO> {
        val exceptionDTO = e.message?.run {
            val formattedMessage = substringAfter("Key ").substringBefore("]")
            ExceptionDTO(formattedMessage)
        } ?: ExceptionDTO("Some sql exception occurred")

        return ResponseEntity(
            exceptionDTO,
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(e: ResponseStatusException): ResponseEntity<ExceptionDTO> {
        return when (e.reason) {
            null -> ResponseEntity(e.statusCode)
            else -> ResponseEntity(ExceptionDTO(e.reason), e.statusCode)
        }
    }
}