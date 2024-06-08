package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.wand.*

data class PlaceSpellInLootAction(val spell: Spell) : GameAction("Place spell in loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        check(oldState.loot.spells.none { it.id == spell.id }) { "Spell is already in loot" }
        return Result.success(
            oldState.copy(
                loot = oldState.loot.copy(
                    spells = oldState.loot.spells + spell
                )
            )
        )
    }
}
