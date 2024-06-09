package de.moyapro.colors.wand.util

import de.moyapro.colors.util.*
import io.kotest.matchers.*
import org.junit.*
import java.util.*
import kotlin.random.*

class HashUuidFunctionsTest {
    @Test
    fun generateUuidTwice() {
        val uuid1: UUID = HashUuidFunctions.v5("the input string")
        val uuid2: UUID = HashUuidFunctions.v5("the input string")
        uuid1 shouldBe uuid2
    }
    @Test
    fun generateUuidTwiceWithRandom() {
        val random1 = Random(3634534)
        val uuid1: UUID = HashUuidFunctions.v5(random1.nextDouble().toString())
        val random2 = Random(3634534)
        val uuid2: UUID = HashUuidFunctions.v5(random2.nextDouble().toString())
        uuid1 shouldBe uuid2
    }

    @Test
    fun generateAnother() {
        val uuid1: UUID = HashUuidFunctions.v5("the input string")
        val uuid2: UUID = HashUuidFunctions.v5("another input string")
        uuid1 shouldNotBe uuid2
    }
}
