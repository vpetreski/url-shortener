package io.vanja.shorty

import jakarta.validation.ConstraintViolationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

data class Violation(val fieldName: String, val message: String)
data class ValidationError(var violations: MutableList<Violation> = mutableListOf())
data class RestError(var message: String?)

@Component
class Util {
    fun toBase62(number: Long): String {
        return toBase(number, 62)
    }

    private fun toBase(number: Long, base: Int): String {
        val alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        if (number < base) {
            return alphabet[number.toInt()].toString()
        }

        var value = number
        val sb = StringBuilder()
        while (value != 0L) {
            val remind = (value % base).toInt()
            value = (value - remind) / base
            sb.append(alphabet[remind])
        }

        return sb.toString()
    }

    /**
     * Returns simple error message based on list of constraint violations.
     *
     * It concatenates field names with error messages and adding a '.' in the end, so we form sentences.
     */
    fun restError(e: ConstraintViolationException): RestError {
        val validationError = ValidationError()
        e.constraintViolations.forEach {
            validationError.violations.add(
                Violation(
                    it.propertyPath.toString(),
                    it.message
                )
            )
        }

        return RestError(validationError.violations.joinToString(". ") {
            val field: String = if (it.fieldName.contains(".")) {
                it.fieldName.split(".").last().replaceFirstChar(Char::uppercaseChar)
            } else {
                it.fieldName
            }
            field + " " + it.message
        })
    }

    fun restError(e: Throwable): RestError {
        return RestError(e.message)
    }
}