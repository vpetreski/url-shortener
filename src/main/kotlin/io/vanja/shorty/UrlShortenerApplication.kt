package io.vanja.shorty

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArcCloudApplication

fun main(args: Array<String>) {
    runApplication<ArcCloudApplication>(*args)
}
