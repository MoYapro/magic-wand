package de.moyapro.colors.util

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.takeTwo.EnemyId
import de.moyapro.colors.takeTwo.Mage
import de.moyapro.colors.takeTwo.MageId
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.wand.MagicSlot

fun List<Wand>.replace(
    wandId: WandId,
    replacementWand: Wand
): List<Wand> {
    return this.map { currentWand -> if (currentWand.id == wandId) replacementWand else currentWand }
}

fun List<Enemy>.replace(
    enemyId: EnemyId,
    replacementEnemy: Enemy
): List<Enemy> {
    return this.map { currentEnemy -> if (currentEnemy.id == enemyId) replacementEnemy else currentEnemy }
}

fun List<Mage>.replace(
    enemyId: MageId,
    replacementEnemy: Mage
): List<Mage> {
    return this.map { currentEnemy -> if (currentEnemy.id == enemyId) replacementEnemy else currentEnemy }
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
