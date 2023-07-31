package com.atendimento.market.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

/**
 * Classe responsável por tratar exceções globais da aplicação e fornecer respostas de erro apropriadas.
 */

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorMessage = "Ocorreu um erro interno no servidor."
        val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorMessage = "Recurso não encontrado."
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorMessage = ex.message ?: "Argumento inválido."
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.map { fieldError: FieldError ->
            val fieldName = fieldError.field
            val errorMessage = fieldError.defaultMessage ?: "Campo inválido."
            FieldErrorDTO(fieldName, errorMessage)
        }
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Erro de validação.")
        errorResponse.fieldErrors = errors
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    data class ErrorResponse(val status: Int, val message: String) {
        var fieldErrors: List<FieldErrorDTO>? = null
    }

    data class FieldErrorDTO(val field: String, val message: String)
}
