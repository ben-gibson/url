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

    @Test
    fun canAddPath() {
        val path = Path.create("/foo/bar/")
        val other = Path.create("/baz/qaz/")

        assertThat(path.with(other).toString()).isEqualTo("foo/bar/baz/qaz")
    }

    @Test
    fun canAddParameter() {
        val path = Path.create("/foo/bar/")

        assertThat(path.with("id", "some-id").toString()).isEqualTo("foo/bar/id/some-id")
        assertThat(path.with("my slug", "some slug").toString()).isEqualTo("foo/bar/my%20slug/some%20slug")
    }
}