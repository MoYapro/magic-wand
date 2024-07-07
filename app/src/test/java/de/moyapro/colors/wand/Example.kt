package de.moyapro.colors.wand

import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
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
                Spell(name = "spell", magicSlots = listOf(MagicSlot(Magic(type = MagicType.GREEN))))
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

fun getExampleSpell() = Spell(name = "SpellName", magicSlots = listOf(MagicSlot(Magic())))

fun getExampleAchievement() = Achievement.ARCHIEVED_SOMETHING

fun getExampleEnemy() = Enemy(name = "Example Enemy", health = 10, possibleActions = listOf(SelfHealEnemyAction()))

fun getExampleRunData() = RunData(
    activeWands = listOf(getExampleWandWithTwoSlots().first),
    mages = getExampleMages(),
    spells = listOf(getExampleSpell()),
    wandsInBag = listOf(getExampleWandWithTwoSlots().first)

)

fun getExampleMagic() = Magic()

fun getExampleFight() = FightData(
    currentTurn = 1,
    battleBoard = BattleBoard(emptyList()),
    fightHasEnded = FightOutcome.ONGOING,
    mages = getExampleMages(),
    wands = listOf(getExampleWandWithSingleSlot().first),
    magicToPlay = listOf(getExampleMagic())
)


fun getExampleMages() = listOf(
    Mage(MAGE_I_ID, health = 10, wandId = WAND_I_ID),
    Mage(MAGE_II_ID, health = 10, wandId = WAND_II_ID),
    Mage(MAGE_III_ID, health = 10, wandId = WAND_III_ID),
)