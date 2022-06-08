package uk.co.ben_gibson.url

data class Path private constructor(private val path: String) {

    companion object {
        fun create(value: String) = Path(value.trim { it.isWhitespace() || it == '/' })
    }

    override fun toString() = path
}
