package uk.co.ben_gibson.url

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

internal class QueryStringTest {

    companion object {
        @JvmStatic
        private fun equalityExpectationsProvider() = Stream.of(
            Arguments.of(QueryString.create("foo=bar"), QueryString.create("foo=bar"), true),
            Arguments.of(QueryString.create("  foo=bar  "), QueryString.create("foo=bar"), true),
            Arguments.of(QueryString.create("?foo=bar&bar=baz"), QueryString.create("foo=bar&bar=baz"), true),
            Arguments.of(QueryString.create("foo=bar"), QueryString.create("foo=baz"), false),
            Arguments.of(QueryString.create("foobar=bar"), QueryString.create("foo=bar"), false),
        )
    }

    @Test
    fun canCastToString() {
        val query = QueryString.create("foo=bar&bar=baz");

        assertThat(query.toString()).isEqualTo("foo=bar&bar=baz")
    }

    @ParameterizedTest
    @ValueSource(strings = ["?foo=bar&bar=baz", "  ?foo=bar&bar=baz    "])
    fun canBeNormalised(value: String) {
        assertThat(QueryString.create(value).toString()).isEqualTo("foo=bar&bar=baz")
    }

    @Test
    fun canCastToMap() {
        val query = QueryString.create("foo=bar&bar=foo&bar=baz");

        assertThat(query.toMap()).isEqualTo(mapOf("foo" to listOf("bar"), "bar" to listOf("foo", "baz")))
    }

    @Test
    fun canCreateFromMap() {
        val query = QueryString.fromMap(mapOf("foo" to listOf("bar"), "bar" to listOf("foo", "baz")));

        assertThat(query.toString()).isEqualTo("foo=bar&bar=foo&bar=baz")
    }

    @ParameterizedTest
    @ValueSource(strings = [ "abc" ])
    fun rejectsInvalidQueryString(value: String) {
        assertThatThrownBy { QueryString.create(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid query string '${value}'")
    }

    @ParameterizedTest
    @MethodSource("equalityExpectationsProvider")
    fun canDetermineIfEqual(query: QueryString, other: QueryString, expected: Boolean) {
        assertThat(query == other).isEqualTo(expected)
    }

    @Test
    fun canAddNewParameterValue() {
        val query = QueryString.create("foo=bar&bar=baz")
        val updatedQuery = query.withParameter("baz", "qaz")

        assertThat(updatedQuery.toString()).isEqualTo("foo=bar&bar=baz&baz=qaz")
    }

    @Test
    fun canAddExistingParameterValue() {
        val query = QueryString.create("foo=bar&bar=baz")
        val updatedQuery = query.withParameter("foo", "baz")

        assertThat(updatedQuery.toString()).isEqualTo("foo=bar&foo=baz&bar=baz")
    }

    @Test
    fun canAddNewParameterValues() {
        val query = QueryString.create("foo=bar&bar=baz");
        val updatedQuery = query.withParameter("baz", listOf("foo", "bar", "baz"))

        assertThat(updatedQuery.toString()).isEqualTo("foo=bar&bar=baz&baz=foo&baz=bar&baz=baz")
    }

    @Test
    fun canAddExistingParameterValues() {
        val query = QueryString.create("foo=bar&bar=baz");
        val updatedQuery = query.withParameter("foo", listOf("foo", "baz", "qaz"))

        assertThat(updatedQuery.toString()).isEqualTo("foo=bar&foo=foo&foo=baz&foo=qaz&bar=baz")
    }
}