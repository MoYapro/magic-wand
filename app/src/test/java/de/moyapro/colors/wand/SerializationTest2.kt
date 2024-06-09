package de.moyapro.colors.wand

import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*
import io.kotest.matchers.equality.*
import org.junit.*

class SerializationTest2 {

    @Test
    fun wandSerialization() {
        val wands = listOf(createExampleWand(), createExampleWand())
        val json = getConfiguredJson().writeValueAsString(wands)
        getConfiguredJson().readValue<List<Wand>>(json)
            .first() shouldBeEqualUsingFields wands.first()
        getConfiguredJson().readValue<List<Wand>>(json).last() shouldBeEqualUsingFields wands.last()
    }

}