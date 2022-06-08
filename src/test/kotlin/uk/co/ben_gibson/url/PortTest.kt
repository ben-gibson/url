package uk.co.ben_gibson.url

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PortTest {

    @Test
    fun canCastToString() {
        val port = Port(8080)

        assertThat(port.toString()).isEqualTo("8080")
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 443, 22, 65534 ])
    fun acceptsValidPorts(value: Int) {
        val port = Port(value)

        assertThat(port.port).isEqualTo(value)
    }

    @ParameterizedTest
    @ValueSource(ints = [ 0, -1, 65535 ])
    fun rejectsInvalidPorts(value: Int) {
        assertThatThrownBy { Port(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid port number '${value}'")
    }

    @Test
    fun canDetermineIfEqual() {
        val port = Port(8080)

        assertThat(port == Port(8080)).isTrue
        assertThat(port == Port(8888)).isFalse
    }
}