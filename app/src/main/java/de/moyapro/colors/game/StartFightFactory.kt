package de.moyapro.colors.game

import de.moyapro.colors.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.wand.*

object StartFightFactory {
    fun createInitialState(wands: List<Wand>? = null): MyGameState {
        val mageId = MageId(0)
        val wandId = WandId()
        val mages = listOf(
            Mage(id = mageId, health = 5, wandId = wandId),
            Mage(id = MageId(1), health = 6),
            Mage(id = MageId(2), health = 7),
        )

        val actualWands = wands ?: listOf(createStarterWand(mageId = mages.first().id, wandId = wandId))
        return MyGameState(enemies = listOf(createExampleEnemy(1)),
            wands = actualWands,
            magicToPlay = listOf(createExampleMagic()),
            currentTurn = 0,
            mages = mages,
            loot = Loot(wands = listOf(createExampleWand()), spells = listOf(Spell(name = "Foo", magicSlots = MagicType.values().map {
                MagicSlot(requiredMagic = Magic(type = it))
            })))
        )
    }

    private fun createStarterWand(mageId: MageId, wandId: WandId): Wand {
        val spell1 = Spell(
            name = "Bolt", magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.SIMPLE)))
        )
        val spell2 = Spell(
            name = "Double", magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.GREEN)))
        )
        val slot1 = Slot(level = 0, power = 2, spell = spell1)
        val slot2 = Slot(level = 1, power = 2, spell = spell2)
        return Wand(
            id = wandId, mageId = mageId, slots = listOf(slot1, slot2)
        )
    }

}
