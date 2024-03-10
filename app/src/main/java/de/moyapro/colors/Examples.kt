package de.moyapro.colors

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.actions.SelfHealEnemyAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.MagicType
import de.moyapro.colors.wand.Spell
import kotlin.random.Random


fun createExampleWand() =
    Wand(
        slots = listOf(
            createExampleSlot(Spell("Blitz"), level = 0, 2, power = 1),
            createExampleSlot(Spell("Pow"), level = 1, 1, power = 10),
            createExampleSlot(Spell("Bom"), level = 0, 5, power = 1),
            createExampleSlot(Spell("Top"), level = 2, 1, power = 1),
        )
    )

fun createExampleEnemy(health: Int = kotlin.random.Random.nextInt(1, 10)) = Enemy(
    health = health,
    possibleActions = listOf(SelfHealEnemyAction())
)

fun createExampleSlot(spell: Spell = Spell("Pew"), level: Int = 0, requiredMagic: Int = 1, power: Int = 1): Slot {
    val random = Random(99)
    return Slot(
        level = level,
        spell = spell,
        magicSlots = (1..requiredMagic).map { createExampleMagicSlot(MagicType.values().random(random)) },
        power = power
    )
}

fun createExampleMagicSlot(type: MagicType = MagicType.SIMPLE) = MagicSlot(requiredMagic = Magic(type = type))
fun createExampleMagic(type: MagicType = MagicType.SIMPLE) = Magic(type = type)
