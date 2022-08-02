package uk.co.ben_gibson.url

import java.net.URLDecoder
import java.net.URLEncoder

data class QueryString private constructor(private val query: String) {
    init {
        query.matches(Regex("^([\\w\\-~&/%+?\\[\\]]+(=([\\w\\-~&/%+])+)+)+\$")) || throw IllegalArgumentException("Invalid query string '${query}'")
    }

    companion object {
        fun create(value: String) = QueryString(value.trim().trimStart('?'))

        fun fromMap(parameters: Map<String, List<String>>) : QueryString {
            val queryString = parameters.entries
                .map { it -> encode(it.key) to it.value.map { encode(it) } }
                .flatMap { parameter -> parameter.second.map { "${parameter.first}=${it}" }}
                .joinToString("&")

            return QueryString(queryString)
        }

        private fun encode(value: String) = URLEncoder.encode(value, Charsets.UTF_8)
        private fun decode(value: String) = URLDecoder.decode(value, Charsets.UTF_8)
    }

    fun toMap() : Map<String, List<String>> {
        return query.split("&")
            .map {
                val (left, right) = it.split("=")

                decode(left) to decode(right)
            }
            .groupBy({ it.first }, { it.second })
    }

    fun withParameter(key: String, values: List<String>) : QueryString {
        val parameters = toMap().toMutableMap()

        val existingValues = parameters.getOrDefault(key, listOf())

        parameters[encode(key)] = existingValues + values.map { encode(it) }

        return fromMap(parameters)
    }

    fun withParameter(key: String, value: String) : QueryString {
        return withParameter(key, listOf(value))
    }

    fun withoutParameter(key: String) : QueryString {
        val parameters = toMap().toMutableMap()

        parameters.remove(key)

        return fromMap(parameters)
    }

    override fun toString() = query
}
