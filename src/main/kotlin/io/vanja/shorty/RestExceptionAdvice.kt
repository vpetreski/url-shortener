package io.vanja.shorty

import jakarta.validation.ConstraintViolationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class RestExceptionAdvice(private val util: Util) {
    @ExceptionHandler(value = [ConstraintViolationException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(e: ConstraintViolationException): RestError {
        return util.restError(e)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class, UrlException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): RestError {
        return util.restError(e)
    }
}