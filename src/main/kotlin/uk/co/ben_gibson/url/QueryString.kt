package uk.co.ben_gibson.url

import java.net.URLDecoder.decode
import java.net.URLEncoder.encode

data class QueryString private constructor(private val query: String) {
    init {
        query.matches(Regex("^([\\w\\-~&%+?\\[\\]]+(=([\\w\\-~&%+])+)+)+\$")) || throw IllegalArgumentException("Invalid query string '${query}'")
    }

    companion object {
        fun create(value: String) = QueryString(value.trim().trimStart('?'))

        fun fromMap(parameters: Map<String, List<String>>) : QueryString {
            val queryString = parameters.entries
                .map { it -> encode(it.key, "UTF-8") to it.value.map { encode(it, "UTF-8") } }
                .flatMap { parameter -> parameter.second.map { "${parameter.first}=${it}" }}
                .joinToString("&")

            return QueryString(queryString)
        }
    }

    fun toMap() : Map<String, List<String>> {
        return query.split("&")
            .map {
                val (left, right) = it.split("=")

                decode(left, "UTF-8") to decode(right, "UTF-8")
            }
            .groupBy({ it.first }, { it.second })
    }

    fun withParameter(key: String, values: List<String>) : QueryString {
        val parameters = toMap().toMutableMap()

        val existingValues = parameters.getOrDefault(key, listOf())

        parameters[key] = existingValues + values

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
