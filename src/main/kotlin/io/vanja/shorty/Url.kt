package io.vanja.shorty

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime

@Entity
open class Url(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null,

    @Size(max = 8000)
    @NotNull
    @Column(name = "long", nullable = false)
    open var long: String? = null,

    @Size(max = 255)
    @NotNull
    @Column(name = "key", nullable = false)
    open var key: String? = "",

    open var created: OffsetDateTime? = OffsetDateTime.now()
)