package de.moyapro.colors.game.model

data class MagicSlot(
    val id: MagicSlotId = MagicSlotId(),
    val requiredMagic: Magic,
    val placedMagic: Magic? = null,
) {
    constructor(requiredMagic: Magic) : this(
        id = MagicSlotId(),
        requiredMagic = requiredMagic,
        placedMagic = null
    )
}

fun MagicSlot.hasRequiredMagic(): Boolean = placedMagic != null
