package de.moyapro.colors

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.actions.SelfHealEnemyAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.Spell
import java.util.Random


fun createExampleWand() =
    Wand(
        slots = listOf(
            createExampleSlot(Spell("Blitz"), level = 0, 2),
            createExampleSlot(Spell("Pow"), level = 1, 1),
            createExampleSlot(Spell("Bom"), level = 0, 5),
            createExampleSlot(Spell("Top"), level = 2, 1),
        )
    )

fun createExampleEnemy() = Enemy(
    health = kotlin.random.Random.nextInt(1, 10),
    possibleActions = listOf(SelfHealEnemyAction())
)

fun createExampleSlot(spell: Spell = Spell("Pew"), level: Int = 0, requiredMagic: Int = 1) = Slot(
    level = level,
    spell = spell,
    magicSlots = (1..requiredMagic).map { createExampleMagicSlot() },
    power = Random().nextInt(10)
)

fun createExampleMagicSlot() = MagicSlot(requiredMagic = Magic())
fun createExampleMagic() = Magic()
