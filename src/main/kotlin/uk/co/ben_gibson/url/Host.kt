package uk.co.ben_gibson.url

data class Host private constructor(private val host: String) {
    companion object {
        operator fun invoke(value: String) = Host(value.trim().trimEnd('/').lowercase())
    }

    override fun toString() = host
}