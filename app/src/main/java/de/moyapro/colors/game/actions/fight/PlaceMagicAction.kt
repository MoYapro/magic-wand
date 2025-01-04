package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.SlotId
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.accessor.findWand
import de.moyapro.colors.game.model.accessor.updateWand
import de.moyapro.colors.game.model.gameState.GameState

data class PlaceMagicAction(
    val wandId: WandId,
    val slotId: SlotId,
    val magicToPlace: Magic,
) : GameAction("Place Magic") {

    override val randomSeed = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        check(oldState.currentFight.magicToPlay.contains(magicToPlace)) { "Magic to place is not available in magicToPlay" }
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
