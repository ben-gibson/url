package uk.co.ben_gibson.url

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PathTest {

    @Test
    fun canCastToString() {
        val path = Path("foo/bar/baz");

        assertThat(path.toString()).isEqualTo("foo/bar/baz")
    }

    @Test
    fun canBeNormalised() {
        assertThat(Path("  /foo/bar/baz/ ").toString()).isEqualTo("foo/bar/baz")
    }

    @Test
    fun canDetermineIfEqual() {
        val path = Path("  /foo/bar/baz/  ")

        assertThat(path == Path("foo/bar/baz")).isTrue
        assertThat(path == Path("foo/bar/baz/qaz")).isFalse
    }

    @Test
    fun canAddPath() {
        val path = Path("/foo/bar/")
        val other = Path("/baz/qaz/")

        assertThat(path.with(other).toString()).isEqualTo("foo/bar/baz/qaz")
    }

    @Test
    fun canAddPathFromString() {
        val path = Path("/foo/bar/")
        val other = "/baz/qaz/"

        assertThat(path.with(other).toString()).isEqualTo("foo/bar/baz/qaz")
    }

    @Test
    fun canAddSegment() {
        val path = Path("/foo/bar/")

        assertThat(path.withSegment("my slug").toString()).isEqualTo("foo/bar/my%20slug")
    }

    @Test
    fun canBeCreatedFromSegments() {
        val segments = listOf("baz", "awesome", "stuff 123")

        assertThat(Path.fromSegments(segments).toString()).isEqualTo("baz/awesome/stuff%20123")
    }

    @Test
    fun canAddSegments() {
        val path = Path("/foo/bar/")

        assertThat(path.withSegments(listOf("baz", "awesome", "stuff 123")).toString()).isEqualTo("foo/bar/baz/awesome/stuff%20123")
    }
}