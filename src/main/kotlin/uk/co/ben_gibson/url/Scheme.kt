package uk.co.ben_gibson.url

data class Scheme private constructor(private val scheme: String) {

    init {
        scheme.matches(Regex("^[a-z]([a-z\\d+.-]+)?$", RegexOption.IGNORE_CASE)) || throw IllegalArgumentException("Invalid scheme '${scheme}'")
    }

    companion object {
        fun create(value: String) = Scheme(value.trim().lowercase())

        fun https() = Scheme("https")
        fun http() = Scheme("http")
    }

    fun isScheme(value: String) = scheme == value
    fun isHttps() = isScheme("https")
    fun isHttp() = isScheme("http")

    override fun toString() = scheme
}