package uk.co.ben_gibson.url

import com.google.common.net.UrlEscapers

data class Path private constructor(private val path: String) {

    companion object {
        fun create(value: String) = Path(value.trim { it.isWhitespace() || it == '/' })
    }

    override fun toString() = path

    fun with(path: Path) = Path("${this.path}/${path}")

    fun with(name: String, value: String) : Path {
        val encodedName = UrlEscapers.urlPathSegmentEscaper().escape(name)
        val encodedValue = UrlEscapers.urlPathSegmentEscaper().escape(value)

        return Path("${this.path}/${encodedName}/${encodedValue}")
    }
}
