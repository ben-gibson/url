package uk.co.ben_gibson.url

import com.google.common.net.UrlEscapers

data class Path private constructor(private val path: String) {

    companion object {
        operator fun invoke(value: String) = Path(normalise(value))

        private fun normalise(value: String) = value.trim { it.isWhitespace() || it == '/' }
    }

    override fun toString() = path

    fun with(path: Path) = Path("${this.path}/${path}")

    fun with(path: String) = with(Path(normalise(path)))

    fun withSegment(segment: String) : Path {
        val encoded = UrlEscapers.urlPathSegmentEscaper().escape(normalise(segment))

        return with(Path(encoded))
    }

    fun withSegments(segments: List<String>) : Path =
        segments.fold(this) { path, segment -> path.withSegment(segment) }
}
