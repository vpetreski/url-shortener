package io.vanja.shorty

import jakarta.validation.constraints.NotBlank
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import java.lang.IllegalArgumentException

@Service
@Validated
class UrlService(
    private val urlRepository: UrlRepository,
    private val util: Util
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun shorten(long: String) : Url {
        if (!long.startsWith("http://", ignoreCase = true) && !long.startsWith("https://", ignoreCase = true)) {
            throw IllegalArgumentException("URL has to start with http:// or https://")
        }

        var url = urlRepository.findByLong(long)

        if (url == null) {
            url = urlRepository.save(Url(long = long))
            url.key = util.toBase62(url.id!!)
            url = urlRepository.save(url)
        }

        return url
    }

    fun redirect(@NotBlank key: String) : String {
        return urlRepository.findByKey(key)!!.long ?: throw DoesNotExistException("URL doesn't exist!")
    }
}