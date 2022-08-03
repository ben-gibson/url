URL
===

![Build](https://github.com/ben-gibson/url/workflows/Build/badge.svg)
![Release](https://img.shields.io/github/v/release/ben-gibson/url)
[![Stand With Ukraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg)](https://stand-with-ukraine.pp.ua)

A Kotlin port of the URL value object library https://github.com/krixon/url

# Prerequisites

- Java 1.8

# Installation

## Gradle
```
implementation("uk.co.ben_gibson:url:0.0.6")
```

## Maven
```xml
<dependency>
  <groupId>uk.co.ben_gibson</groupId>
  <artifactId>url</artifactId>
  <version>0.0.6</version>
</dependency>
```

# Usage

## URL
```kotlin
URL.fromString("https://example.com:8080/foo/bar?limit=10#L1-L10")

// OR

URL(
    scheme = Scheme.https(),
    host = Host("example.com"),
    port = Port(8080),
    path = Path("foo/bar/baz"),
    queryString = QueryString.fromMap(mapOf("order" to listOf("id", "name"))),
    fragment = Fragment("L10")
)

```

## Path
```kotlin
val path = Path("foo/bar/")
    .with("/baz/slug/")
    .withSegment("my slug")
    .withSegments("that", "is", "awesome")

path.toString() // -> foo/bar/baz/slug/my%20slug/that/is/awesome

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