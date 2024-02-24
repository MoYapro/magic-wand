package de.moyapro.colors.util

import de.moyapro.colors.wand.Wand
import java.util.UUID

fun List<Wand>.replace(
    wandId: UUID,
    replacementWand: Wand
): List<Wand> {
    return this.map { currentWand: Wand -> if (currentWand.id == wandId) replacementWand else currentWand }
}
