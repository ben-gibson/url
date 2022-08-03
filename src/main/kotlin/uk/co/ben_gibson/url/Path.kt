package uk.co.ben_gibson.url

import com.google.common.net.UrlEscapers

data class Path private constructor(private val path: String) {

    companion object {
        operator fun invoke(value: String) = Path(normalise(value))
        fun fromSegments(segments: List<String>) = segments.map { Path(encode(it)) }.reduce { a, b -> a.with(b) }

        private fun normalise(value: String) = value.trim { it.isWhitespace() || it == '/' }

        private fun encode(value: String) = UrlEscapers.urlPathSegmentEscaper().escape(value)
    }

    override fun toString() = path

    fun with(path: Path) = Path("${this.path}/${path}")

    fun with(path: String) = with(Path(normalise(path)))

    fun withSegment(segment: String) = with(Path(encode(segment)))

    fun withSegments(segments: List<String>) = segments.fold(this) { path, segment -> path.withSegment(segment) }
}
