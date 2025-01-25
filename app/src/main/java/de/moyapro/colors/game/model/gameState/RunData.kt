package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.interfaces.HasMages

data class RunData(
    override val mages: List<Mage>,
    val spells: List<Spell<*>>,
    val activeWands: List<Wand>,
    val wandsInBag: List<Wand>,
    val generators: List<MagicGenerator>,
) : HasMages {
}
