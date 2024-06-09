package de.moyapro.colors.game.model


data class Magic(
    val id: MagicId = MagicId(),
    val type: MagicType = MagicType.SIMPLE,
)
