package io.vanja.shorty

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

class IntegrationTest(
    @Value("\${info.app.expire}")
    private val expireInDays: Int,
    @Autowired val urlService: UrlService,
    @Autowired val urlRepository: UrlRepository,
    @Autowired val util: Util
) : BaseIntegrationTest() {
    @Test
    fun `Test URL shortener`() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy {
            urlService.shorten("")
        }.withMessage("URL has to start with http:// or https://")

        Assertions.assertThatExceptionOfType(UrlException::class.java).isThrownBy {
            urlService.redirect("xyz")
        }.withMessage("URL doesn't exist!")

        val longUrl = "https://www.amazon.com/Premium-Certified-Lunt-Solar-Eclipse/dp/B01NB09NHK/ref=sr_1_5?crid=PN1YC9S25IID&keywords=solar+eclipse+glasses&qid=1696508571&sprefix=%2Caps%2C168&sr=8-5"
        var url = urlService.shorten(longUrl)

        assertThat(url.long).isEqualTo(longUrl)
        assertThat(url.key).isEqualTo(util.toBase62(url.id!!))
        assertThat(urlService.redirect(url.key!!)).isEqualTo(longUrl)

        url.created = OffsetDateTime.now().minusDays((expireInDays + 1).toLong())
        url = urlRepository.save(url)
        Assertions.assertThatExceptionOfType(UrlException::class.java).isThrownBy {
            urlService.redirect(url.key!!)
        }.withMessage("Link expired!")
    }
}