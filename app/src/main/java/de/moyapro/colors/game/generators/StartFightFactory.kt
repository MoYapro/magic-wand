package de.moyapro.colors.game.generators

import android.content.ContentValues.TAG
import android.util.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

object StartFightFactory {
    fun setupFightStage(): NewGameState {
//            loot = Loot(wands = listOf(createExampleWand()), spells = listOf(Spell(name = "Foo", magicSlots = MagicType.values().map {
//                MagicSlot(requiredMagic = Magic(type = it))
//            })))
        val newGameState = NewGameState(
            currentFight = initialFightData(),
            currentRun = initialRunData(),
            options = initialGameOptions(),
            progression = initialProgressionData(),
        )
        Log.d(TAG, getConfiguredJson().writerWithDefaultPrettyPrinter().writeValueAsString(newGameState))
        return newGameState
    }

    private fun initialProgressionData() = ProgressionData(
        achievements = emptyList(),
        unlockedEnemies = emptyList(),
        unlockedWands = emptyList(),
        unlockedSpells = emptyList()
    )

    private fun initialGameOptions() = GameOptions(
        thisIsAnOption = true
    )

    private fun initialRunData() = RunData(
        activeWands = emptyList(),
        mages = emptyList(),
        spells = emptyList(),
        wandsInBag = listOf(createStarterWand()),
        generators = emptyList(),
    )

    private fun initialFightData(): FightData {
        val wandsAndMages = getInitialMages().map { mage ->
            val wand = createExampleWand(mageId = mage.id, readyToZap = true)
            val mageWithWand = mage.copy(wandId = wand.id)
            kotlin.Pair(mageWithWand, wand)
        }
        return FightData(
            currentTurn = 0,
            fightState = FightState.ONGOING,
            battleBoard = initialBattleBoard(),
            wands = wandsAndMages.map { it.second },
            magicToPlay = listOf(createExampleMagic()),
            mages = wandsAndMages.map { it.first },
        )
    }

    private fun initialBattleBoard() = BattleBoard(
        fields = listOf(
            Field(FieldId(0), null, Terrain.SAND),
            Field(FieldId(1), null, Terrain.PLAIN),
            Field(FieldId(2), null, Terrain.ROCK),
            Field(FieldId(3), null, Terrain.FORREST),
            Field(FieldId(4), null, Terrain.WATER),
            Field(FieldId(5), createExampleEnemy(), Terrain.PLAIN),
            Field(FieldId(6), createExampleEnemy(), Terrain.ROCK),
            Field(FieldId(7), createExampleEnemy(), Terrain.FORREST),
            Field(FieldId(8), createExampleEnemy(), Terrain.WATER),
            Field(FieldId(9), createExampleEnemy(), Terrain.SAND),
            Field(FieldId(10), null, Terrain.SAND),
            Field(FieldId(11), null, Terrain.PLAIN),
            Field(FieldId(12), null, Terrain.ROCK),
            Field(FieldId(13), null, Terrain.FORREST),
            Field(FieldId(14), null, Terrain.WATER),
        )
    )

    private fun getInitialMages(): List<Mage> = listOf(
        Mage(id = MageId(0), health = 5),
        Mage(id = MageId(1), health = 6),
        Mage(id = MageId(2), health = 7),
    )

    private fun createStarterWand(): Wand {
        val spell1 = Bonk(
            magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.SIMPLE)))
        )
        val spell2 = Splash(
            magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.GREEN)))
        )
        val slot1 = Slot(level = 0, power = 2, spell = spell1)
        val slot2 = Slot(level = 1, power = 2, spell = spell2)
        return Wand(slots = listOf(slot1, slot2))
    }

}
