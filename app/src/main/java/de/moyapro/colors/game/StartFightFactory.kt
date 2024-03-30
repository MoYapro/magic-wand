package de.moyapro.colors.game

import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMagic
import de.moyapro.colors.takeTwo.Mage
import de.moyapro.colors.takeTwo.MageId
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.MagicType
import de.moyapro.colors.wand.Spell

object StartFightFactory {
    fun createInitialState(wands: List<Wand>? = null): MyGameState {
        val mageId = MageId()
        val wandId = WandId()
        val mages = listOf(Mage(id = mageId, health = 5, wandId = wandId))

        val actualWands =
            wands ?: mages.map { mage -> createStarterWand(mageId = mage.id, wandId = wandId) }
        return MyGameState(
            enemies = listOf(createExampleEnemy(1)),
            wands = actualWands,
            magicToPlay = listOf(createExampleMagic()),
            currentTurn = 0,
            mages = mages,
        )
    }

    private fun createStarterWand(mageId: MageId, wandId: WandId): Wand {
        val spell1 = Spell(
            name = "Bolt",
            magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.SIMPLE)))
        )
        val spell2 = Spell(
            name = "Double",
            magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.GREEN)))
        )
        val slot1 = Slot(level = 0, power = 2, spell = spell1)
        val slot2 = Slot(level = 1, power = 2, spell = spell2)
        return Wand(
            id = wandId,
            mageId = mageId,
            slots = listOf(slot1, slot2)
        )
    }

}
