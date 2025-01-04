package de.moyapro.colors.wand.util

import android.util.Log
import de.moyapro.colors.util.castOrNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

class TypeHelper {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            mockkStatic(Log::class)
            every { Log.d(any(), any()) } returns 1
            every { Log.e(any(), any()) } returns 1
            every { Log.i(any(), any()) } returns 1
        }
    }


    @Test
    fun castOrNull_cast() {
        val any: Any = 4
        val int: Int? = castOrNull(any)
        int shouldBe 4
    }

    @Test
    fun castOrNull_null() {
        val any: Any = "4"
        val int: Int? = castOrNull(any)
        int shouldBe null
    }

    @Test
    fun castOrNull_isnull() {
        val any: Any? = null
        val int: Int? = castOrNull(any)
        int shouldBe null
    }
}