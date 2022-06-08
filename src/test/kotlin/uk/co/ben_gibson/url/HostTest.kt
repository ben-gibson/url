package uk.co.ben_gibson.url

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

internal class HostTest {
    companion object {
        @JvmStatic
        private fun equalityExpectationsProvider() = Stream.of(
            Arguments.of(Host.create("www.example.com"), Host.create("www.example.com"), true),
            Arguments.of(Host.create("www.example.com/"), Host.create("www.example.com"), true),
            Arguments.of(Host.create("example.com"), Host.create("example.com"), true),
            Arguments.of(Host.create("EXAMPLE.com"), Host.create("EXAMPLE.com"), true),
            Arguments.of(Host.create("ExAmPle.CoM"), Host.create("example.com"), true),
            Arguments.of(Host.create("example.com"), Host.create("other.com"), false),
        )
    }

    @Test
    fun canCastToString() {
        val host = Host.create("www.example.com");

        assertThat(host.toString()).isEqualTo("www.example.com")
    }

    @Test
    fun canBeNormalised() {
        assertThat(Host.create("WwW.ExAmPlE.CoM/").toString()).isEqualTo("www.example.com")
    }

    @ParameterizedTest
    @ValueSource(strings = [ "foo" ])
    fun rejectsInvalidHost(value: String) {
        assertThatThrownBy { Host.create(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid host '${value}'")
    }

    @ParameterizedTest
    @MethodSource("equalityExpectationsProvider")
    fun canDetermineIfEqual(host: Host, other: Host, expected: Boolean) {
        assertThat(host == other).isEqualTo(expected)
    }
}