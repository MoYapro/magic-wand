package de.moyapro.colors.wand

import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.MagicType.BLUE
import de.moyapro.colors.game.model.MagicType.GREEN
import de.moyapro.colors.game.model.MagicType.RED
import de.moyapro.colors.game.model.MagicType.SIMPLE
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

val MAGE_I_ID = MageId(1)
val MAGE_II_ID = MageId(2)
val MAGE_III_ID = MageId(3)

val WAND_I_ID = WandId()
val WAND_II_ID = WandId()
val WAND_III_ID = WandId()

fun getExampleWandWithSingleSlot(slot: Slot? = null, spell: Spell? = null): Pair<Wand, Slot> {
    val actualSlot = slot ?: Slot(level = 0, power = 1)
    val actualSpell = spell ?: Spell(name = "spell", magicSlots = listOf(MagicSlot(Magic())))
    val newWand: Wand =
        Wand(slots = listOf(actualSlot)).putSpell(slotId = actualSlot.id, actualSpell)
    return Pair(newWand, actualSlot)
}

fun getExampleWandWithTwoSlots(): Triple<Wand, Slot, Slot> {
    val slot1 = Slot(level = 0, power = 1)
    val slot2 =
        Slot(level = 0, power = 2)
    val newWand: Wand =
        Wand(slots = listOf(slot1, slot2))
            .putSpell(
                slotId = slot1.id,
                Spell(name = "spell", magicSlots = listOf(MagicSlot(Magic())))
            )
            .putSpell(
                slotId = slot2.id,
                Spell(name = "spell", magicSlots = listOf(MagicSlot(Magic(type = GREEN))))
            )
    return Triple(newWand, slot1, slot2)
}

fun getExampleGameState() = NewGameState(
    currentRun = getExampleRunData(),
    currentFight = getExampleFight(),
    options = getExampleOptions(),
    progression = getExampleProgression(),
)


fun getExampleOptions() = GameOptions(thisIsAnOption = true)

fun getExampleProgression() = ProgressionData(
    unlockedSpells = listOf(getExampleSpell()),
    unlockedWands = listOf(getExampleWandWithSingleSlot().first),
    unlockedEnemies = listOf(getExampleEnemy()),
    achievements = listOf(getExampleAchievement()),
)

fun getExampleSpell(magicType: MagicType = SIMPLE) = Spell(name = "SpellName", magicSlots = listOf(MagicSlot(Magic(type = magicType))))

fun getExampleAchievement() = Achievement.ARCHIEVED_SOMETHING

fun getExampleEnemy() = Enemy(name = "Example Enemy", health = 10, possibleActions = listOf(SelfHealEnemyAction()))

fun getExampleRunData(): RunData {
    val mages = getExampleMages()
    val activeWands = mages.map { getExampleWand(it.id) }
    val magesWithWands = activeWands.map { mages.findMage(it.mageId!!)?.copy(wandId = it.id)!! }
    return RunData(
        activeWands = activeWands,
        mages = magesWithWands,
        spells = listOf(getExampleSpell()),
        wandsInBag = listOf(getExampleWandWithTwoSlots().first)
    )
}

fun getExampleWand(
    mageId: MageId? = null,
    slot: Slot = Slot(level = 0, power = 1),
    spell: Spell = Spell(name = "spell", magicSlots = listOf(MagicSlot(Magic()))),
): Wand {
    val someSlots = listOf(SIMPLE, GREEN, BLUE, RED).map { magicType -> getExampleSlot(magicType = magicType) }
    val newWand: Wand = Wand(mageId = mageId, slots = listOf(slot) + someSlots).putSpell(slotId = slot.id, spell)
    return newWand
}

fun getExampleSlot(magicType: MagicType = SIMPLE): Slot {
    return Slot(
        level = 3,
        power = 2,
        spell = getExampleSpell(magicType)
    )
}

fun getExampleFight(): FightData {
    val magics = listOf(SIMPLE, GREEN, BLUE, RED)
    return FightData(
        currentTurn = 1,
        battleBoard = BattleBoard(emptyList()),
        fightHasEnded = FightOutcome.ONGOING,
        mages = getExampleMages(),
        wands = getExampleMages().map { getExampleWand(it.id, getExampleSlot()) },
        magicToPlay = magics.map { Magic(type = it) } + magics.map { Magic(type = it) } + magics.map { Magic(type = it) }
    )
}

fun getExampleMages() = listOf(
    Mage(MAGE_I_ID, health = 10, wandId = WAND_I_ID),
    Mage(MAGE_II_ID, health = 10, wandId = WAND_II_ID),
    Mage(MAGE_III_ID, health = 10, wandId = WAND_III_ID),
)