package de.moyapro.colors.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

private val objectMapper: ObjectMapper by lazy {
    ObjectMapper().registerKotlinModule()
}

fun getConfiguredJson() = objectMapper