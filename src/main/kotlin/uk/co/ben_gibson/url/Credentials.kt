package uk.co.ben_gibson.url

data class Credentials(val username: String, val password: String? = null) {
    fun withoutPassword() = Credentials(username)

    override fun toString() : String {
        return password?.let { username.plus(":${it}") } ?: username
    }
}