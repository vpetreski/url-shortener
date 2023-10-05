CREATE TABLE shorty_url
(
    id   BIGINT        NOT NULL,
    long VARCHAR(8000) NOT NULL,
    key  VARCHAR(255)  NOT NULL,
    CONSTRAINT shorty_url_pk PRIMARY KEY (id),
    CONSTRAINT shorty_url_long_unique UNIQUE (long),
    CONSTRAINT shorty_url_short_unique UNIQUE (key)
)