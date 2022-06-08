package uk.co.ben_gibson.url

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class URLTest {

    @Test
    fun canCreateFromString() {
        val url = URL.fromString("https://example.com:8080/foo/bar?limit=10#L1-L10")

        assertThat(url.toString()).isEqualTo("https://example.com:8080/foo/bar?limit=10#L1-L10")
    }

    @Test
    fun canConvertToHttps() {
        val url = URL.fromString("http://example.com")

        assertThat(url.toString()).isEqualTo("http://example.com")
        assertThat(url.toHttps().toString()).isEqualTo("https://example.com")
    }

    @Test
    fun canConvertToHttp() {
        val url = URL.fromString("https://example.com")

        assertThat(url.toString()).isEqualTo("https://example.com")
        assertThat(url.toHttp().toString()).isEqualTo("http://example.com")
    }

    @Test
    fun canRemovePort() {
        val url = URL.fromString("https://example.com:8080")

        assertThat(url.toString()).isEqualTo("https://example.com:8080")
        assertThat(url.withoutPort().toString()).isEqualTo("https://example.com")
    }

    @Test
    fun canRemovePath() {
        val url = URL.fromString("https://example.com/foo/bar")

        assertThat(url.toString()).isEqualTo("https://example.com/foo/bar")
        assertThat(url.withoutPath().toString()).isEqualTo("https://example.com")
    }

    @Test
    fun canRemoveQueryString() {
        val url = URL.fromString("https://example.com/foo/bar?limit=10")

        assertThat(url.toString()).isEqualTo("https://example.com/foo/bar?limit=10")
        assertThat(url.withoutQueryString().toString()).isEqualTo("https://example.com/foo/bar")
    }

    @Test
    fun canRemoveFragment() {
        val url = URL.fromString("https://example.com/foo/bar#L1-L10")

        assertThat(url.toString()).isEqualTo("https://example.com/foo/bar#L1-L10")
        assertThat(url.withoutFragment().toString()).isEqualTo("https://example.com/foo/bar")
    }

    @Test
    fun canAddPort() {
        val url = URL.fromString("https://example.com")

        assertThat(url.toString()).isEqualTo("https://example.com")
        assertThat(url.withPort(Port(443)).toString()).isEqualTo("https://example.com:443")
    }

    @Test
    fun canAddPath() {
        val url = URL.fromString("https://example.com")

        assertThat(url.toString()).isEqualTo("https://example.com")
        assertThat(url.withPath(Path.create("foo/bar")).toString()).isEqualTo("https://example.com/foo/bar")
    }

    @Test
    fun canAddQueryString() {
        val url = URL.fromString("https://example.com")

        assertThat(url.toString()).isEqualTo("https://example.com")
        assertThat(url.withQueryString(QueryString.create("foo=bar")).toString()).isEqualTo("https://example.com?foo=bar")
    }

    @Test
    fun canAddFragment() {
        val url = URL.fromString("https://example.com")

        assertThat(url.toString()).isEqualTo("https://example.com")
        assertThat(url.withFragment(Fragment.create("L10-L15")).toString()).isEqualTo("https://example.com#L10-L15")
    }

    @Test
    fun canAddScheme() {
        val url = URL.fromString("https://example.com")

        assertThat(url.toString()).isEqualTo("https://example.com")
        assertThat(url.withScheme(Scheme.create("ftp")).toString()).isEqualTo("ftp://example.com")
    }

    @Test
    fun canAddHost() {
        val url = URL.fromString("https://example.com")

        assertThat(url.toString()).isEqualTo("https://example.com")
        assertThat(url.withHost(Host.create("new-example.co.uk")).toString()).isEqualTo("https://new-example.co.uk")
    }

    @Test
    fun canGetCredentials() {
        val url = URL.fromString("https://foo:bar@example.com")

        assertThat(url.credentials).isEqualTo(Credentials("foo", "bar"))
    }
}