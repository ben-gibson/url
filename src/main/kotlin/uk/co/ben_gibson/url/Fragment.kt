package uk.co.ben_gibson.url

data class Fragment private constructor(private val fragment: String) {

    init {
        fragment.matches(Regex("^[?%!\$&'()*+,;=a-zA-Z\\d-._~:@/]*")) || throw IllegalArgumentException("Invalid fragment")
    }

    companion object {
        operator fun invoke(value: String) = Fragment(value.trim().trimStart('#'))
    }

    override fun toString() = fragment
}
