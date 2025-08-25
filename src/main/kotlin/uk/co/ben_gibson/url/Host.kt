package uk.co.ben_gibson.url

data class Host private constructor(private val host: String) {
    companion object {
        operator fun invoke(value: String) = Host(value.trim().trimEnd('/'))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Host) return false

        return this.host.equals(other.host, ignoreCase = true)
    }

    override fun toString() = host

    override fun hashCode(): Int {
        return host.lowercase().hashCode()
    }
}