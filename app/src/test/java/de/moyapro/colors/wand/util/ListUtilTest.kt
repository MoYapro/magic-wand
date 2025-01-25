package de.moyapro.colors.game.util

import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.MagicType
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.util.mapFirst
import de.moyapro.colors.util.mapIf
import de.moyapro.colors.util.minus
import de.moyapro.colors.util.replace
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Test

internal class ListUtilTest {

    @Test
    fun replaceWand() {
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

    @Test
    fun replaceMagicSlot() {
        val suitableSlot = MagicSlot(requiredMagic = Magic(type = MagicType.GREEN))
        val replacement = suitableSlot.copy(placedMagic = Magic(type = MagicType.GREEN))
        val magicSlots = listOf(
            MagicSlot(requiredMagic = Magic(), placedMagic = Magic()),
            suitableSlot
        )
        magicSlots.last().placedMagic shouldBe null
        magicSlots.replace(suitableSlot, replacement).last().placedMagic shouldNotBe null
    }

    @Test
    fun plusMinus() {
        val list = listOf(1, 2, 3)
        val extenedList = list + 4
        val reducedList = extenedList - 2
        reducedList shouldContainExactly listOf(1, 3, 4)
    }

    @Test
    fun mapIf() {
        val input = listOf(1, 2, 3)
        input.mapIf({ it % 2 == 1 }) { it + 1 } shouldBe listOf(2, 2, 4)
    }

    @Test
    fun mapFirst() {
        val input = listOf(1, 2, 3)
        input.mapFirst({ it % 2 == 1 }) { it + 1 } shouldBe listOf(2, 2, 3)
    }

    @Test
    fun mapFirstWithDuplicate() {
        val input = listOf(1, 2, 1)
        input.mapFirst({ it % 2 == 1 }) { it + 1 } shouldBe listOf(2, 2, 1)
    }

    @Test
    fun mapFirstWithDuplicateObjects() {
        val replaceMe = Magic()
        val input = listOf(replaceMe, Magic(type = MagicType.GREEN), Magic())
        input.mapFirst({ it.type == MagicType.SIMPLE }) { Magic(type = MagicType.GREEN) }
            .map { it.type } shouldBe listOf(MagicType.GREEN, MagicType.GREEN, MagicType.SIMPLE)
    }
}
