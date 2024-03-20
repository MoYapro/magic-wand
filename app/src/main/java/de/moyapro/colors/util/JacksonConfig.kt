package de.moyapro.colors.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun getConfiguredJson(): ObjectMapper {
    val objectMapper = ObjectMapper()

    objectMapper.registerKotlinModule()
    return objectMapper
}