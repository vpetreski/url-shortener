package io.vanja.shorty

import org.springframework.beans.factory.annotation.Autowired

// TODO
class IntegrationTest(
    @Autowired val urlService: UrlService,
) : BaseIntegrationTest() {
}