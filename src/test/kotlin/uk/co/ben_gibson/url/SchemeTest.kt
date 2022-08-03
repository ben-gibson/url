package uk.co.ben_gibson.url

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

internal class SchemeTest {

    companion object {
        @JvmStatic
        private fun equalityExpectationsProvider() = Stream.of(
            Arguments.of(Scheme("https"), Scheme("https"), true),
            Arguments.of(Scheme("  https  "), Scheme("https"), true),
            Arguments.of(Scheme("https"), Scheme("http"), false),
            Arguments.of(Scheme("https"), Scheme("ftp"), false),
        )
    }

    @Test
    fun canCreateHttpScheme() {
        val scheme = Scheme.http();

        assertThat(scheme.toString()).isEqualTo("http")
    }

    @Test
    fun canCreateHttpsScheme() {
        val scheme = Scheme.https();

        assertThat(scheme.toString()).isEqualTo("https")
    }

    @Test
    fun canDetermineIfHttps() {
        assertThat(Scheme.https().isHttps()).isTrue
        assertThat(Scheme.http().isHttps()).isFalse
    }

    @Test
    fun canDetermineIfHttp() {
        assertThat(Scheme.http().isHttp()).isTrue
        assertThat(Scheme.https().isHttp()).isFalse
    }

    @Test
    fun canBeNormalised() {
        assertThat(Scheme("  https ").toString()).isEqualTo("https")
    }

    @ParameterizedTest
    @ValueSource(strings = [ "1ftp" ])
    fun rejectsInvalidScheme(value: String) {
        assertThatThrownBy { Scheme(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid scheme '${value}'")
    }

    @ParameterizedTest
    @MethodSource("equalityExpectationsProvider")
    fun canDetermineIfEqual(scheme: Scheme, other: Scheme, expected: Boolean) {
        assertThat(scheme == other).isEqualTo(expected)
    }
}