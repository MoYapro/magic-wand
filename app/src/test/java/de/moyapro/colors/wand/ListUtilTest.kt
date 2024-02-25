package de.moyapro.colors.wand

import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.replace
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class ListUtilTest {

    @Test
    fun replace() {
        val otherWand1 = Wand()
        val otherWand2 = Wand()
        val initialWand = Wand()
        val replacementWand = Wand()
        val wandList =
            listOf(otherWand1, initialWand, otherWand2).replace(initialWand.id, replacementWand)
        wandList[0] shouldBe otherWand1
        wandList[1] shouldBe replacementWand
        wandList[2] shouldBe otherWand2
    }
}
