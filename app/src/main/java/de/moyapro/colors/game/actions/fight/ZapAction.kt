package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

data class ZapAction(
    val wandId: WandId,
    override val target: FieldId? = null,
) : GameAction("Zap") {

    override val randomSeed = this.hashCode()

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        val wandToZap = oldState.currentFight.wands.findById(wandId)
        check(wandToZap != null) { "Wand to zap does not exist for id $wandId" }
        val zappedWand = wandToZap.copy(slots = removeMagicFromFullSlots(wandToZap))
        val updatedBattleBoard = wandToZap.affect(oldState.currentFight.battleBoard, target)

        return Result.success(
            oldState.copy(
                currentFight = oldState.currentFight.copy(
                    battleBoard = updatedBattleBoard,
                    wands = oldState.currentFight.wands.replace(zappedWand)
                )
            )
        )
    }

    override fun onAddAction(actions: MutableList<GameAction>) {
        actions.add(ShowTargetSelectionAction(this))
    }

    override fun requireTargetSelection() = true

    private fun removeMagicFromFullSlots(wand: Wand): List<Slot> = wand.slots.map { slot ->
        if (slot.hasRequiredMagic() && slot.spell != null) {
            val updatedSpell =
                slot.spell.copy(magicSlots = slot.spell.magicSlots.map { magicSlot ->
                    magicSlot.copy(placedMagic = null)
                })
            slot.copy(spell = updatedSpell)
        } else {
            slot
        }
    }

    override fun isValidTarget(field: BattleBoard, id: FieldId): Boolean {
        return true
    }

    override fun withSelection(targetFieldId: FieldId): GameAction {
        return this.copy(target = targetFieldId)
    }

}
