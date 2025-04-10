package de.moyapro.colors

import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.enemy.actions.AttackMageEnemyAction
import de.moyapro.colors.game.enemy.actions.SelfHealEnemyAction
import de.moyapro.colors.game.model.EnemyId
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.MagicType
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.game.model.gameState.Field
import de.moyapro.colors.game.model.gameState.Terrain
import de.moyapro.colors.game.spell.Bonk
import kotlin.random.Random
import kotlin.random.nextInt


fun createExampleWand(mageId: MageId? = null, readyToZap: Boolean = false) =
    Wand(
        mageId = mageId,
        slots = listOf(
            createExampleSlot(
                level = 0, 2, power = 3, readyToZap
            ),
            createExampleSlot(
                level = 1, 1, power = 11, readyToZap
            ),
            createExampleSlot(level = 0, 5, power = 4, readyToZap),
            createExampleSlot(level = 2, 1, power = 7, readyToZap),
        )
    )

fun createExampleWand(mageId: MageId? = null, vararg spells: Spell<*>) = Wand(
    mageId = mageId,
    slots = spells.mapIndexed { index, spell -> Slot(level = index, spell = spell, power = 1) }
)

fun createExampleEnemy(health: Int = Random.nextInt(1, 1000), breadth: Int = 1, size: Int = 1) = Enemy(
    health = health,
    breadth = breadth,
    size = size,
    possibleActions = listOf(SelfHealEnemyAction(), AttackMageEnemyAction()),
    statusEffects = Effect.values().associateWith { Random.nextInt(0..100) }
)

fun createExampleMage(health: Int = 1, wandId: WandId? = null, mageId: MageId) =
    Mage(id = mageId, health = health, wandId = wandId)

fun createExampleSlot(
    level: Int = 0,
    requiredMagic: Int = 1,
    power: Int = 1,
    readyToZap: Boolean = false,
): Slot {
    val random = Random(99)
    return Slot(
        level = level,
        spell = Bonk(
            magicSlots = (1..requiredMagic).map {
                createExampleMagicSlot(
                    MagicType.values().random(random),
                    readyToZap,
                )
            }),
        power = power
    )
}

fun createExampleSpell(): Spell<*> {
    return Bonk()
}

fun createExampleMagicSlot(type: MagicType = MagicType.SIMPLE, readyToZap: Boolean = false): MagicSlot {
    val magic = Magic(type = type)
    return MagicSlot(requiredMagic = magic, placedMagic = if (readyToZap) magic else null)
}

fun createExampleMagic(type: MagicType = MagicType.SIMPLE) = Magic(type = type)


fun createExampleBattleBoardFilledWith(enemyOnAllFields: Enemy?) = BattleBoard((0..14).map { i -> Field(FieldId(i.toShort()), enemyOnAllFields?.copy(id = EnemyId()), Terrain.PLAIN) })