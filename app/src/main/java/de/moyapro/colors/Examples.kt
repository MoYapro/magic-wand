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
import kotlin.random.Random
import kotlin.random.nextInt


fun createExampleWand(mageId: MageId? = null, vararg additionalSlots: Slot = emptyArray()) = Wand(
    mageId = mageId,
    slots = listOf(
        createExampleSlot(level = 0, power = 3),
        createExampleSlot(level = 1, power = 11),
        createExampleSlot(level = 0, power = 4),
        createExampleSlot(level = 2, power = 7),
        *additionalSlots
    )
)

fun createExampleWand(mageId: MageId? = null, vararg spells: Spell<*>) = Wand(
    mageId = mageId, slots = spells.mapIndexed { index, spell -> Slot(level = index, spell = spell, power = 1) })

fun createExampleEnemy(health: Int = Random.nextInt(1, 1000), breadth: Int = 1, size: Int = 1) = Enemy(
    health = health, breadth = breadth, size = size, possibleActions = listOf(SelfHealEnemyAction(), AttackMageEnemyAction()), statusEffects = Effect.values().associateWith { Random.nextInt(0..100) })

fun createExampleMage(health: Int = 1, wandId: WandId? = null, mageId: MageId) = Mage(id = mageId, health = health, wandId = wandId)

fun createExampleSlot(
    level: Int = 0,
    power: Int = 1,
    spell: Spell<*>? = null,
): Slot {
    return Slot(
        level = level, spell = spell, power = power
    )
}


fun createExampleMagicSlot(type: MagicType = MagicType.SIMPLE, readyToZap: Boolean = false): MagicSlot {
    val magic = Magic(type = type)
    return MagicSlot(requiredMagic = magic, placedMagic = if (readyToZap) magic else null)
}

fun createExampleMagic(type: MagicType = MagicType.SIMPLE) = Magic(type = type)


fun createExampleBattleBoardFilledWith(enemyOnAllFields: Enemy?) = BattleBoard((0..14).map { i -> Field(FieldId(i.toShort()), enemyOnAllFields?.copy(id = EnemyId()), Terrain.PLAIN) })