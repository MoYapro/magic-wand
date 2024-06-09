package de.moyapro.colors.util

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*

private val objectMapper: ObjectMapper by lazy {
    ObjectMapper().registerKotlinModule()
}

fun getConfiguredJson() = objectMapper