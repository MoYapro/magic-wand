package de.moyapro.colors.game.generators

import android.content.ContentValues.TAG
import android.util.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

object StartFightFactory {
    fun setupFightStage(wands: List<Wand>? = null, fightState: FightData? = null): NewGameState {
//            loot = Loot(wands = listOf(createExampleWand()), spells = listOf(Spell(name = "Foo", magicSlots = MagicType.values().map {
//                MagicSlot(requiredMagic = Magic(type = it))
//            })))
        val newGameState = NewGameState(
            currentFight = fightState ?: initialFightData(wands),
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
        wandsInBag = listOf(createStarterWand())
    )

    private fun initialFightData(wands: List<Wand>?): FightData {
        val wandsAndMages = getInitialMages().map { mage ->
            val wand = createExampleWand(mageId = mage.id)
            val mageWithWand = mage.copy(wandId = wand.id)
            kotlin.Pair(mageWithWand, wand)
        }
        return FightData(
            currentTurn = 0,
            fightState = FightState.ONGOING,
            battleBoard = initialBattleBoard(),
            wands = wands ?: wandsAndMages.map { it.second },
            magicToPlay = listOf(createExampleMagic()),
            mages = wandsAndMages.map { it.first },
        )
    }

    private fun initialBattleBoard() = BattleBoard(
        fields = listOf(
            Field(null, Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(null, Terrain.FORREST),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),
            Field(createExampleEnemy(), Terrain.PLAIN),
            Field(createExampleEnemy(), Terrain.ROCK),
            Field(createExampleEnemy(), Terrain.FORREST),
            Field(createExampleEnemy(), Terrain.WATER),
            Field(createExampleEnemy(), Terrain.SAND),
            Field(null, Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(null, Terrain.FORREST),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),
        )
    )

    private fun getInitialMages(): List<Mage> = listOf(
        Mage(id = MageId(0), health = 5),
        Mage(id = MageId(1), health = 6),
        Mage(id = MageId(2), health = 7),
    )

    private fun createStarterWand(): Wand {
        val spell1 = Spell(
            name = "Bolt", magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.SIMPLE)))
        )
        val spell2 = Spell(
            name = "Double", magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.GREEN)))
        )
        val slot1 = Slot(level = 0, power = 2, spell = spell1)
        val slot2 = Slot(level = 1, power = 2, spell = spell2)
        return Wand(slots = listOf(slot1, slot2))
    }

}
