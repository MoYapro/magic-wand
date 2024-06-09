package de.moyapro.colors.game.generators

import android.content.ContentValues.TAG
import android.util.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*

object StartFightFactory {
    fun setupFightStage(wands: List<Wand>? = null, fightState: MyGameState? = null): MyGameState {
        val actualMages = fightState?.mages?.nullIfEmpty() ?: getInitialMages()
        val actualWands = wands ?: listOf(createStarterWand())
        val myGameState = MyGameState(
            enemies = listOf(createExampleEnemy(1)),
            wands = actualWands,
            magicToPlay = listOf(createExampleMagic()),
            currentTurn = 0,
            mages = actualMages,
            loot = Loot(wands = listOf(createExampleWand()), spells = listOf(Spell(name = "Foo", magicSlots = MagicType.values().map {
                MagicSlot(requiredMagic = Magic(type = it))
            })))
        )
        Log.d(TAG, getConfiguredJson().writerWithDefaultPrettyPrinter().writeValueAsString(myGameState))
        return myGameState
    }

    private fun getInitialMages(): List<Mage> = listOf(
        Mage(id = MageId(0), health = 5),
        Mage(id = MageId(1), health = 6),
        Mage(id = MageId(2), health = 7),
    )

    private fun createStarterWand(): Wand {
        val spell1 = Spell(
            name = "Bolt", magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.SIMPLE)))
        )
        val spell2 = Spell(
            name = "Double", magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.GREEN)))
        )
        val slot1 = Slot(level = 0, power = 2, spell = spell1)
        val slot2 = Slot(level = 1, power = 2, spell = spell2)
        return Wand(slots = listOf(slot1, slot2))
    }

}
