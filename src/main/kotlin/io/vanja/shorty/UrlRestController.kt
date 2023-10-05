package io.vanja.shorty

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UrlRestController(
    @Value("\${info.app.permanent}")
    private val permanent: Boolean,
    private val urlService: UrlService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/shorten")
    fun shorten(@RequestParam("long") long: String): ResponseEntity<String> {
        return ResponseEntity.ok("TODO")
    }

    @GetMapping("/")
    fun redirect(@RequestParam("key") key: String): ResponseEntity<Void> {
        // TODO
        return ResponseEntity.status(if (permanent) HttpStatus.MOVED_PERMANENTLY else HttpStatus.FOUND)
            .header("location", "TODO")
            .build()
    }
}