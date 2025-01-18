package de.moyapro.colors.game.generators

import android.content.ContentValues.*
import android.util.Log
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.enemy.blueprints.Grunt
import de.moyapro.colors.game.enemy.blueprints.Slime
import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.MagicType
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Splash
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.game.model.gameState.Field
import de.moyapro.colors.game.model.gameState.GameOptions
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.model.gameState.ProgressionData
import de.moyapro.colors.game.model.gameState.RunData
import de.moyapro.colors.game.model.gameState.Terrain
import de.moyapro.colors.game.model.gameState.notStartedFight
import de.moyapro.colors.util.getConfiguredJson
import java.util.Random

object Initializer {
    fun createInitialGameState(): GameState {
//            loot = Loot(wands = listOf(createExampleWand()), spells = listOf(Spell(name = "Foo", magicSlots = MagicType.values().map {
//                MagicSlot(requiredMagic = Magic(type = it))
//            })))
        val gameState = GameState(
            currentFight = notStartedFight(),
            currentRun = initialRunData(),
            options = initialGameOptions(),
            progression = initialProgressionData(),
        )
        Log.d(TAG, getConfiguredJson().writerWithDefaultPrettyPrinter().writeValueAsString(gameState))
        return gameState
    }

    private fun initialProgressionData() = ProgressionData(
        achievements = emptyList(), unlockedEnemies = emptyList(), unlockedWands = emptyList(), unlockedSpells = emptyList()
    )

    private fun initialGameOptions() = GameOptions(
        thisIsAnOption = true
    )

    private fun initialRunData(): RunData {
        val wandsAndMages = getInitialMages().map { mage ->
            val wand = createExampleWand(mageId = mage.id, readyToZap = true)
            val mageWithWand = mage.copy(wandId = wand.id)
            Pair(mageWithWand, wand)
        }
        return RunData(
            activeWands = wandsAndMages.map { it.second },
            mages = wandsAndMages.map { it.first },
            spells = emptyList(),
            wandsInBag = listOf(createStarterWand()),
            generators = generateMagicGenerators(),
        )
    }

    private fun generateMagicGenerators(): List<MagicGenerator> {
        return listOf(
            MagicGenerator(MagicType.SIMPLE, amountRange = 0..2, randomSeed = Random().nextInt()),
            MagicGenerator(MagicType.RED, amountRange = 0..2, randomSeed = Random().nextInt()),
            MagicGenerator(MagicType.SIMPLE, amountRange = 0..2, randomSeed = Random().nextInt()),
            MagicGenerator(MagicType.SIMPLE, amountRange = 0..2, randomSeed = Random().nextInt())
        )
    }


    fun initialBattleBoard() = BattleBoard(
        fields = listOf(
            Field(FieldId(0), null, Terrain.SAND),
            Field(FieldId(1), null, Terrain.PLAIN),
            Field(FieldId(2), null, Terrain.ROCK),
            Field(FieldId(3), null, Terrain.FORREST),
            Field(FieldId(4), null, Terrain.WATER),
            Field(FieldId(5), Grunt(), Terrain.PLAIN),
            Field(FieldId(6), Grunt(), Terrain.ROCK),
            Field(FieldId(7), Slime(), Terrain.FORREST),
            Field(FieldId(8), Slime(), Terrain.WATER),
            Field(FieldId(9), Grunt(), Terrain.SAND),
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
