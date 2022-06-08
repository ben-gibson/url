package uk.co.ben_gibson.url

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

internal class FragmentTest {

    companion object {
        @JvmStatic
        private fun equalityExpectationsProvider() = Stream.of(
            Arguments.of(Fragment.create("#bar"), Fragment.create("bar"), true),
            Arguments.of(Fragment.create("  bar  "), Fragment.create("bar"), true),
            Arguments.of(Fragment.create("foo"), Fragment.create("bar"), false),
        )
    }

    @Test
    fun canCastToString() {
        val fragment = Fragment.create("bar");

        assertThat(fragment.toString()).isEqualTo("bar")
    }

    @ParameterizedTest
    @ValueSource(strings = ["#bar", "  #bar    "])
    fun canBeNormalised(value: String) {
        assertThat(Fragment.create(value).toString()).isEqualTo("bar")
    }

    @ParameterizedTest
    @MethodSource("equalityExpectationsProvider")
    fun canDetermineIfEqual(fragment: Fragment, other: Fragment, expected: Boolean) {
        assertThat(fragment == other).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(strings = [ "^", "#^", "£", "™" ])
    fun rejectsInvalidFragment(value: String) {
        Assertions.assertThatThrownBy { QueryString.create(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid query string '${value}'")
    }
}