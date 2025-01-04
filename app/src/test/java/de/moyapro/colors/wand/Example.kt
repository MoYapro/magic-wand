package de.moyapro.colors.game

import de.moyapro.colors.createExampleBattleBoardFilledWith
import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMagic
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.game.actions.fight.EndTurnAction
import de.moyapro.colors.game.actions.fight.HitMageAction
import de.moyapro.colors.game.actions.fight.PlaceMagicAction
import de.moyapro.colors.game.actions.fight.ShowTargetSelectionAction
import de.moyapro.colors.game.actions.fight.TargetSelectedAction
import de.moyapro.colors.game.actions.fight.ZapAction
import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.enemy.actions.SelfHealEnemyAction
import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.MagicType
import de.moyapro.colors.game.model.MagicType.*
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.SlotId
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.gameState.Achievement
import de.moyapro.colors.game.model.gameState.FightData
import de.moyapro.colors.game.model.gameState.GameOptions
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.model.gameState.ProgressionData
import de.moyapro.colors.game.model.gameState.RunData
import de.moyapro.colors.util.FightState
import de.moyapro.colors.util.MAGE_III_ID
import de.moyapro.colors.util.MAGE_II_ID
import de.moyapro.colors.util.MAGE_I_ID
import de.moyapro.colors.util.WAND_III_ID
import de.moyapro.colors.util.WAND_II_ID
import de.moyapro.colors.util.WAND_I_ID

fun getExampleWandWithSingleSlot(slot: Slot? = null, spell: Spell<*>? = null): Pair<Wand, Slot> {
    val actualSlot = slot ?: Slot(level = 0, power = 1)
    val actualSpell = spell ?: Bonk()
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
                Bonk(magicSlots = listOf(MagicSlot(Magic())))
            )
            .putSpell(
                slotId = slot2.id,
                Bonk(magicSlots = listOf(MagicSlot(Magic(type = GREEN))))
            )
    return Triple(newWand, slot1, slot2)
}

fun getExampleGameState(magicToPlay: List<Magic>? = null) = GameState(
    currentRun = getExampleRunData(),
    currentFight = getExampleFight(magicToPlay),
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

fun getExampleSpell(magicType: MagicType = SIMPLE) = Bonk(magicSlots = listOf(MagicSlot(Magic(type = magicType))))

fun getExampleAchievement() = Achievement.ARCHIEVED_SOMETHING

fun getExampleEnemy() = Enemy(name = "Example Enemy", health = 10, possibleActions = listOf(SelfHealEnemyAction(), HitMageAction(MAGE_I_ID, 1)))

fun getExampleRunData(): RunData {
    val mages = getExampleMages()
    val activeWands = mages.map { getExampleWand(it.id) }
    val magesWithWands = activeWands.map { mages.findMage(it.mageId!!)?.copy(wandId = it.id)!! }
    return RunData(
        activeWands = activeWands,
        mages = magesWithWands,
        spells = listOf(getExampleSpell()),
        wandsInBag = listOf(getExampleWandWithTwoSlots().first),
        generators = listOf(MagicGenerator(SIMPLE, 2..4), MagicGenerator(GREEN, 1..3), MagicGenerator(BLUE, 1..2), MagicGenerator(RED, 1..1))
    )
}

fun getExampleWand(
    mageId: MageId? = null,
    slot: Slot = Slot(level = 0, power = 1),
    spell: Spell<*> = getExampleSpell(),
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

fun getExampleFight(magicToPlay: List<Magic>?): FightData {
    val magics = listOf(SIMPLE, GREEN, BLUE, RED)
    return FightData(
        currentTurn = 1,
        battleBoard = createExampleBattleBoardFilledWith(createExampleEnemy(10)),
        fightState = FightState.ONGOING,
        mages = getExampleMages(),
        wands = getExampleMages().map { getExampleWand(it.id, getExampleSlot()) },
        magicToPlay = magicToPlay ?: (magics.map { Magic(type = it) } + magics.map { Magic(type = it) } + magics.map { Magic(type = it) }),
        generators = getExampleMagicGenerator()
    )
}

fun getExampleMagicGenerator(): List<MagicGenerator> = listOf(MagicGenerator(amount = 1..2, magicType = SIMPLE))

fun getExampleMages() = listOf(
    Mage(MAGE_I_ID, health = 10, wandId = WAND_I_ID),
    Mage(MAGE_II_ID, health = 10, wandId = WAND_II_ID),
    Mage(MAGE_III_ID, health = 10, wandId = WAND_III_ID),
)

fun createExampleActionList() = listOf(
    PlaceMagicAction(
        magicToPlace = createExampleMagic(),
        slotId = SlotId(),
        wandId = WandId()
    ),
    ZapAction(WandId()),
    NoOp(),
    EndTurnAction(),
    TargetSelectedAction(targetFieldId = FieldId(0)),
    ShowTargetSelectionAction(originalAction = ZapAction(wandId = WandId())),
)
