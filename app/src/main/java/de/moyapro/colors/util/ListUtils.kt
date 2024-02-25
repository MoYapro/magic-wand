package de.moyapro.colors.util

import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId

fun List<Wand>.replace(
    wandId: WandId,
    replacementWand: Wand
): List<Wand> {
    return this.map { currentWand -> if (currentWand.id == wandId) replacementWand else currentWand }
}

fun <T> List<T>.mapIf(predicate: (T) -> Boolean, transformer: (T) -> T): List<T> {
    return this.map { if (predicate(it)) transformer(it) else it }
}
