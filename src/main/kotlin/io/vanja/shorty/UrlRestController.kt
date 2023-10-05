package io.vanja.shorty

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class UrlRestController(
    @Value("\${info.app.permanent}")
    private val permanent: Boolean,
    private val urlService: UrlService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/shorten")
    fun shorten(@RequestParam("long") long: String): ResponseEntity<String> {
        return ResponseEntity.ok(urlService.shorten(long))
    }

    @GetMapping("/{key}")
    fun redirect(@PathVariable("key") key: String): ResponseEntity<Void> {
        return ResponseEntity.status(if (permanent) HttpStatus.MOVED_PERMANENTLY else HttpStatus.FOUND)
            .location(URI.create(urlService.redirect(key)))
            .build()
    }
}