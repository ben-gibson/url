package uk.co.ben_gibson.url

import java.net.MalformedURLException

data class URL(
    val scheme: Scheme,
    val host: Host,
    val port: Port? = null,
    val credentials: Credentials? = null,
    val path: Path? = null,
    val queryString: QueryString? = null,
    val fragment: Fragment? = null
) {
    companion object {
        fun fromString(value: String): URL {
            val url = try {
                java.net.URL(value)
            } catch (e: MalformedURLException) {
                throw IllegalArgumentException("Invalid URL")
            }

            val credentials = url.userInfo?.split(':')?.let { Credentials(it[0], it.getOrNull(1)) }

            return URL(
                Scheme(url.protocol),
                Host(url.host),
                url.port.takeIf { it > -1 }?.let { Port(it) },
                credentials,
                url.path.takeIf { it.isNotEmpty() }?.let { Path(it) },
                url.query?.let { QueryString(it) },
                url.ref?.let { Fragment(it) }
            )
        }
    }

    fun withScheme(value: Scheme) = URL(value, host, port, credentials, path, queryString, fragment)
    fun withHost(value: Host) = URL(scheme, value, port, credentials, path, queryString, fragment)
    fun withPort(value: Port) = URL(scheme, host, value, credentials, path, queryString, fragment)
    fun withPath(value: Path) = URL(scheme, host, port, credentials, value, queryString, fragment)
    fun withQueryString(value: QueryString) = URL(scheme, host, port, credentials, path, value, fragment)
    fun withFragment(value: Fragment) = URL(scheme, host, port, credentials, path, queryString, value)

    fun withoutPort() = URL(scheme, host, null, credentials, path, queryString, fragment)
    fun withoutPath() = URL(scheme, host, port, credentials, null, queryString, fragment)
    fun withoutQueryString() = URL(scheme, host, port, credentials, path, null, fragment)
    fun withoutFragment() = URL(scheme, host, port, credentials, path, queryString, null)

    fun toHttps() = withScheme(Scheme.https())
    fun toHttp() = withScheme(Scheme.http())

    override fun toString(): String {
        return "${scheme}://${host}"
            .plus(port?.let { ":${it}" } ?: "")
            .plus(path?.let { "/${it}" } ?: "")
            .plus(queryString?.let { "?${it}" } ?: "")
            .plus(fragment?.let { "#${it}" } ?: "")
    }
}
