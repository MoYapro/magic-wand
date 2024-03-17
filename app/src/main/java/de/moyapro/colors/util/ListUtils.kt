package de.moyapro.colors.util

import de.moyapro.colors.takeTwo.HasId
import de.moyapro.colors.wand.MagicSlot


fun <T, E: HasId<T>> List<E>.replace(key: T, newValue: E): List<E> {
    return this.map { oldValue -> if (oldValue.id == key) newValue else oldValue }
}

fun List<MagicSlot>.replace(
    suitableMagicSlot: MagicSlot,
    updatedMagicSlot: MagicSlot
): List<MagicSlot> {
    return this.mapFirst({ it == suitableMagicSlot }) { updatedMagicSlot }
}


fun <T> List<T>.mapIf(predicate: (T) -> Boolean, transformer: (T) -> T): List<T> {
    return this.map { if (predicate(it)) transformer(it) else it }
}

fun <T> List<T>.mapFirst(predicate: (T) -> Boolean, transformer: (T) -> T): List<T> {
    var replaced = false
    val firstOccurence = this.first { predicate(it) }
    val firstOccurenceTransformed = transformer(firstOccurence)
    return this.map {
        if (!replaced && it === firstOccurence) {
            replaced = true
            firstOccurenceTransformed
        } else it
    }
}
