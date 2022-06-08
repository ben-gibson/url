package uk.co.ben_gibson.url

data class Port(val port: Int) {

    init {
        port in 1..65534 || throw IllegalArgumentException("Invalid port number '${port}'")
    }

    override fun toString() = port.toString()
}
