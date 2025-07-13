package de.moyapro.colors.game.generators

import android.content.ContentValues.*
import android.util.Log
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.enemy.blueprints.Grunt
import de.moyapro.colors.game.enemy.blueprints.Slime
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.MagicType
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.game.model.gameState.Field
import de.moyapro.colors.game.model.gameState.GameOptions
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.model.gameState.ProgressionData
import de.moyapro.colors.game.model.gameState.RunData
import de.moyapro.colors.game.model.gameState.Terrain
import de.moyapro.colors.game.model.gameState.notStartedFight
import de.moyapro.colors.game.spell.Acid
import de.moyapro.colors.game.spell.Bonk
import de.moyapro.colors.game.spell.Fizz
import de.moyapro.colors.game.spell.Splash
import de.moyapro.colors.util.getConfiguredJson
import java.util.Random

object Initializer {
    fun createInitialGameState(): GameState {
//            stash = Stash(wands = listOf(createExampleWand()), spells = listOf(Spell(name = "Foo", magicSlots = MagicType.values().map {
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
        val (mage1, mage2, mage3) = getInitialMages()
        val wand1 = createStarterWand().copy(mageId = mage1.id)
        val wand2 = createStarterWand(listOf(Fizz(), Bonk())).copy(mageId = mage2.id)
        val wand3 = createStarterWand(listOf(Fizz(), Splash())).copy(mageId = mage3.id)
        val mages = listOf(
            mage1.copy(wandId = wand1.id),
            mage2.copy(wandId = wand2.id),
            mage3.copy(wandId = wand3.id),
        )
        return RunData(
            activeWands = listOf(wand1, wand2, wand3),
            mages = mages,
            spells = emptyList(),
            wandsInBag = listOf(createExampleWand()),
            generators = generateMagicGenerators(),
        )
    }

    private fun generateMagicGenerators(): List<MagicGenerator> {
        return listOf(
            MagicGenerator(MagicType.RED, amountRange = 0..2, randomSeed = Random().nextInt()),
            MagicGenerator(MagicType.RED, amountRange = 0..2, randomSeed = Random().nextInt()),
            MagicGenerator(MagicType.GREEN, amountRange = 0..2, randomSeed = Random().nextInt()),
            MagicGenerator(MagicType.RED, amountRange = 0..2, randomSeed = Random().nextInt())
        )
    }


    fun initialBattleBoard() = BattleBoard(
        fields = listOf(
            Field(FieldId(0), null, Terrain.SAND),
            Field(FieldId(1), null, Terrain.PLAIN),
            Field(FieldId(2), Slime(), Terrain.ROCK),
            Field(FieldId(3), null, Terrain.FORREST),
            Field(FieldId(4), null, Terrain.WATER),
            Field(FieldId(5), null, Terrain.PLAIN),
            Field(FieldId(6), Grunt(), Terrain.ROCK),
            Field(FieldId(7), Grunt(), Terrain.FORREST),
            Field(FieldId(8), Grunt(), Terrain.WATER),
            Field(FieldId(9), null, Terrain.SAND),
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

    private val starterSpells: List<Spell<*>> = listOf(
        Acid(magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.RED)))),
        Splash(magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.GREEN))))
    )


    private fun createStarterWand(spells: List<Spell<*>> = starterSpells): Wand {
        require(spells.size == 2) { "Starter wand must have exactly two spells" }
        val slot1 = Slot(level = 0, power = 2, spell = spells[0])
        val slot2 = Slot(level = 1, power = 2, spell = spells[1])
        return Wand(slots = listOf(slot1, slot2))
    }

}
