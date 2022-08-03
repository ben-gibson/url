package uk.co.ben_gibson.url

data class Host private constructor(private val host: String) {

    init {
        // TODO: Add better validation.
        host.contains(".") || throw IllegalArgumentException("Invalid host '${host}'")
    }

    companion object {
        operator fun invoke(value: String) = Host(value.trim().trimEnd('/').lowercase())
    }

    override fun toString() = host
}