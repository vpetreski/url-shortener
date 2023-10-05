package io.vanja.shorty

import jakarta.validation.constraints.NotBlank
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class UrlService(
    private val urlRepository: UrlRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun foo(@NotBlank bar: String) {

    }
}