package de.moyapro.colors

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.wand.*
import kotlin.random.*


fun createExampleWand(mageId: MageId? = null) =
    Wand(
        mageId = mageId,
        slots = listOf(
            createExampleSlot(
                spellName = "Blitz", level = 0, 2, power = 3
            ),
            createExampleSlot(
                spellName = "Pow", level = 1, 1, power = 11
            ),
            createExampleSlot(spellName = "Bom", level = 0, 5, power = 4),
            createExampleSlot(spellName = "Top", level = 2, 1, power = 7),
        )
    )

fun createExampleEnemy(health: Int = kotlin.random.Random.nextInt(1, 10)) = Enemy(
    health = health,
    possibleActions = listOf(SelfHealEnemyAction())
)

fun createExampleSpell(name: String = "Example Spell"): Spell = Spell(magicSlots = listOf(createExampleMagicSlot()), name = name)

fun createExampleMage(health: Int = 1, wandId: WandId? = null, mageId: MageId) =
    Mage(id = mageId, health = health, wandId = wandId)

fun createExampleSlot(
    spellName: String = "Pew",
    level: Int = 0,
    requiredMagic: Int = 1,
    power: Int = 1,
): Slot {
    val random = Random(99)
    return Slot(
        level = level,
        spell = Spell(name = spellName,
            magicSlots = (1..requiredMagic).map {
                createExampleMagicSlot(
                    MagicType.values().random(random)
                )
            }),
        power = power
    )
}

fun createExampleMagicSlot(type: MagicType = MagicType.SIMPLE) =
    MagicSlot(requiredMagic = Magic(type = type))

fun createExampleMagic(type: MagicType = MagicType.SIMPLE) = Magic(type = type)
