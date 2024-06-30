package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*

data class PlaceMagicAction(
    val wandId: WandId,
    val slotId: SlotId,
    val magicToPlace: Magic,
) : GameAction("Place Magic") {

    override val randomSeed = this.hashCode()

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        val targetWandWithMagic = oldState.currentFight.findWand(wandId).putMagic(slotId, magicToPlace)
        val updatedMagicToPlay = oldState.currentFight.magicToPlay.filter { magic -> magic != magicToPlace }
        check(updatedMagicToPlay.size + 1 == oldState.currentFight.magicToPlay.size) { "Not exactly one magic was used when placing magic" }
        return Result.success(
            oldState.updateCurrentFight(
                wands = oldState.currentFight.updateWand(targetWandWithMagic.getOrThrow()),
                magicToPlay = updatedMagicToPlay
            )
        )
    }

}
