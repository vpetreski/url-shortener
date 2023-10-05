package io.vanja.shorty;

import io.vanja.shorty.Url
import org.springframework.data.jpa.repository.JpaRepository

interface UrlRepository : JpaRepository<Url, Long> {
    fun findByLong(long: String) : Url?
    fun findByKey(key: String) : Url?
}