package io.vanja.shorty

import jakarta.validation.constraints.NotBlank
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

@Service
@Validated
class UrlService(
    @Value("\${info.app.url}") private val baseUrl: String,
    private val urlRepository: UrlRepository,
    private val util: Util
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun shorten(@NotBlank long: String) : String {
        // TODO - check if url is valid

        var url = urlRepository.findByLong(long)

        if (url == null) {
            url = urlRepository.save(Url(long = long))
            url.key = util.toBase62(url.id!!)
            url = urlRepository.save(url)
        }

        return "$baseUrl/${url.key}"
    }

    fun redirect(@NotBlank key: String) : String {
        return urlRepository.findByKey(key)!!.long ?: throw DoesNotExistException("URL doesn't exist!")
    }
}