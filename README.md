# URL Shortener 🔗️

Design scope and back of the envelope estimation:

- Traffic volume - 100 million URLs are generated per day
- Shortened URL is as short as possible
- Shortened URL can be a combination of numbers (0-9) and characters (a-z, A-Z)
- URL shortening: given a long URL => return a much shorter URL
- URL redirecting: given a shorter URL => redirect to the original URL
- URL will expire after configured number of days, which is 365 by default (`info.app.expire` in `application.yml`)
- High availability, scalability, and fault tolerance considerations
- Write operation per second: 100 million / 24 / 3600 = 1160
- Read operation: Assuming ratio of read operation to write operation is 10:1, read operation per second: 1160 * 10 = 11,600
- Assuming the URL shortener service will run for 10 years, this means we must support 100 million * 365 * 10 = 365 billion records
- Assume average URL length is 100
- Storage requirement over 10 years: 365 billion * 100 bytes * 10 years = 365 TB

## Requirements

- JDK 17
- Docker
- [Curl](https://curl.se)
- [Just](https://github.com/casey/just)

## Usage

Start the service with `just run`.

Shorten long URL:

```shell
curl -w '\n' --location 'http://localhost/shorten' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'long=https://www.amazon.com/Premium-Certified-Lunt-Solar-Eclipse/dp/B01NB09NHK/ref=sr_1_5?crid=PN1YC9S25IID&keywords=solar+eclipse+glasses&qid=1696508571&sprefix=%2Caps%2C168&sr=8-5'
```

It will return shortened URL similar to this:

```text
http://localhost/mf
```

Now, let's try redirect and open generated link http://localhost/mf in your browser - it should redirect to the original long URL.

Run tests with `just test`.

## Architecture

- By default, service will use 302 redirect (temporarily moved) because it allows us to do analytics and monetization. However, if that's not important and performance is top priority, then you can set `info.app.permanent` to `true` in `application.yml` and service will then use 301 redirect (permanently moved) where browser will do caching.
- The hash value consists of characters from [0-9, a-z, A-Z], containing 10 + 26 + 26 = 62 possible characters. To figure out the length of hash value, find the smallest n such that 62^n ≥ 365 billion. The system must support up to 365 billion URLs based on the back of the envelope estimation. So we use Base 62 conversion. Alternative could be to use CRC32 hash + collision resolution, but that would require additional hits to DB.
- Infrastructure: Client > Load Balancer > Server > Cache > DB
- Rate limiter: a potential security problem we could face is that malicious users send an overwhelmingly large number of URL shortening requests. Rate limiter helps to filter out requests based on IP address or other filtering rules.
- Server scaling: Since the web tier is stateless, it is easy to scale the web tier by adding or removing web servers.
- Database scaling: Database replication and sharding are common techniques.
- Analytics: data is increasingly important for business success. Integrating an analytics solution to the URL shortener could help to answer important questions like how many people click on a link? When do they click the link? Etc...
- Some monetization ideas: https://bitly.com/pages/pricing
- The security risk with a shortened URL is you cannot tell where you are going when you click the link, you have to trust the sender. Potential mitigation is preview mode. By prepending 'preview' to a URL shortener, the service does not send you directly to the destination website. Instead, this takes you to a landing page that gives you preview of where you will ultimately go.