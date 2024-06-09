package de.moyapro.colors.game.model

data class Mage(
    override val id: MageId = MageId(),
    val health: Int,
    val wandId: WandId? = null,
) : HasId<MageId>