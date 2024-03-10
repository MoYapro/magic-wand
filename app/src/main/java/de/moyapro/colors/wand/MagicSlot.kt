package de.moyapro.colors.wand

import de.moyapro.colors.takeTwo.MagicSlotId

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
