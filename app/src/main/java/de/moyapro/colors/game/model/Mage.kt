package de.moyapro.colors.game.model

import de.moyapro.colors.game.model.interfaces.HasId

data class Mage(
    override val id: MageId = MageId(),
    val health: Int,
    val wandId: WandId? = null,
) : HasId<MageId>