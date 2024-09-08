package de.moyapro.colors

import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import kotlin.random.*


fun createExampleWand(mageId: MageId? = null, readyToZap: Boolean = false) =
    Wand(
        mageId = mageId,
        slots = listOf(
            createExampleSlot(
                spellName = "Blitz", level = 0, 2, power = 3, readyToZap
            ),
            createExampleSlot(
                spellName = "Pow", level = 1, 1, power = 11, readyToZap
            ),
            createExampleSlot(spellName = "Bom", level = 0, 5, power = 4, readyToZap),
            createExampleSlot(spellName = "Top", level = 2, 1, power = 7, readyToZap),
        )
    )

fun createExampleWand(mageId: MageId? = null, vararg spells: Spell) = Wand(
    mageId = mageId,
    slots = spells.mapIndexed { index, spell -> Slot(level = index, spell = spell, power = 1) }
)

fun createExampleEnemy(health: Int = Random.nextInt(1, 1000), breadth: Int = 1, size: Int = 1) = Enemy(
    health = health,
    breadth = breadth,
    size = size,
    possibleActions = listOf(SelfHealEnemyAction())
)

fun createExampleMage(health: Int = 1, wandId: WandId? = null, mageId: MageId) =
    Mage(id = mageId, health = health, wandId = wandId)

fun createExampleSlot(
    spellName: String = "Pew",
    level: Int = 0,
    requiredMagic: Int = 1,
    power: Int = 1,
    readyToZap: Boolean = false,
): Slot {
    val random = Random(99)
    return Slot(
        level = level,
        spell = Spell(name = spellName,
            magicSlots = (1..requiredMagic).map {
                createExampleMagicSlot(
                    MagicType.values().random(random),
                    readyToZap,
                )
            }),
        power = power
    )
}

fun createExampleSpell(): Spell {
    return Spell(name = "The Spell" + Random.nextInt(), magicSlots = listOf(createExampleMagicSlot(readyToZap = true)))
}

fun createExampleMagicSlot(type: MagicType = MagicType.SIMPLE, readyToZap: Boolean = false): MagicSlot {
    val magic = Magic(type = type)
    return MagicSlot(requiredMagic = magic, placedMagic = if (readyToZap) magic else null)
}

fun createExampleMagic(type: MagicType = MagicType.SIMPLE) = Magic(type = type)


fun createExampleBattleBoard(enemyOnAllFields: Enemy) = BattleBoard((0..15).map { i -> Field(FieldId(i.toShort()), enemyOnAllFields, Terrain.PLAIN) })