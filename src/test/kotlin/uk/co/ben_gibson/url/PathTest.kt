package uk.co.ben_gibson.url

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PathTest {

    @Test
    fun canCastToString() {
        val path = Path.create("foo/bar/baz");

        assertThat(path.toString()).isEqualTo("foo/bar/baz")
    }

    @Test
    fun canBeNormalised() {
        assertThat(Path.create("  /foo/bar/baz/ ").toString()).isEqualTo("foo/bar/baz")
    }

    @Test
    fun canDetermineIfEqual() {
        val path = Path.create("  /foo/bar/baz/  ")

        assertThat(path == Path.create("foo/bar/baz")).isTrue
        assertThat(path == Path.create("foo/bar/baz/qaz")).isFalse
    }
}