URL
===

A Kotlin port of the URL value object library https://github.com/krixon/url

# Prerequisites

- Java 1.8

# Installation

## Gradle
```
Implementation("uk.co.ben_gibson.url")
```

# Usage

```kotlin
URL.fromString("https://example.com:8080/foo/bar?limit=10#L1-L10")

// OR

URL(
    scheme = Scheme.https(),
    host = Host.create("example.com"),
    port = Port(8080),
    path = Path.create("foo/bar/baz"),
    queryString = QueryString.fromMap(mapOf("order" to listOf("id", "name"))),
    fragment = Fragment.create("L10")
)
```

# Development

## Build

```
$ ./gradlew build
```

## Run tests

```
$ ./gradlew test
```