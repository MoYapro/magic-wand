package de.moyapro.colors.wand

import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.effect.*
import de.moyapro.colors.game.effect.Effect.ELECTRIFIED
import de.moyapro.colors.game.effect.Effect.WET
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

class WandAffectTest {

    @Test
    fun `wand does damage to target field`() {
        val battleBoard = createExampleBattleBoardFilledWith(TargetDummy(10))
        val wand = createExampleWand(MAGE_I_ID, Bonk())
        val affectedFieldId = battleBoard.fields.first().id

        val updatedBattleBoard = wand.affect(battleBoard, affectedFieldId)

        updatedBattleBoard.fields.findById(affectedFieldId)?.enemy?.health shouldBe 9
        updatedBattleBoard.fields.filterIndexed { index, _ -> index != 0 }.all { it.enemy!!.health == 10 } shouldBe true
    }

    @Test
    fun `wand does apply status effect`() {
        val battleBoard = createExampleBattleBoardFilledWith(enemyOnAllFields = TargetDummy(10))
        val wand = createExampleWand(MAGE_I_ID, Splash())
        val affectedFieldId = battleBoard.fields.first().id

        val updatedBattleBoard = wand.affect(battleBoard, affectedFieldId)
        updatedBattleBoard.fields.first().enemy!!.statusEffects[WET] shouldBe 1
        battleBoard.fields.all { it.enemy!!.statusEffects == emptyMap<Effect, Int>() } shouldBe true
        updatedBattleBoard.fields.filterIndexed { index, _ -> index != 0 }.all { it.enemy!!.statusEffects == emptyMap<Effect, Int>() } shouldBe true
    }

    @Test
    fun `wand does add status effects`() {
        val battleBoard = createExampleBattleBoardFilledWith(enemyOnAllFields = TargetDummy(10))
        val wand = createExampleWand(MAGE_I_ID, Splash())
        val affectedFieldId = battleBoard.fields.first().id

        val updatedBattleBoard = wand.affect(battleBoard, affectedFieldId)
        val updatedTwiceBattleBoard = wand.affect(updatedBattleBoard, affectedFieldId)
        updatedTwiceBattleBoard.fields.first().enemy!!.statusEffects[WET] shouldBe 2
        updatedTwiceBattleBoard.fields.filterIndexed { index, _ -> index != 0 }.all { it.enemy!!.statusEffects == emptyMap<Effect, Int>() } shouldBe true
    }

    @Test
    fun `spell consumes status effects`() {
        val battleBoard = createExampleBattleBoardFilledWith(enemyOnAllFields = TargetDummy(10))
        val wand = createExampleWand(MAGE_I_ID, Splash(), Fizz())
        val affectedFieldId = battleBoard.fields.first().id

        val updatedBattleBoard = wand.affect(battleBoard, affectedFieldId)
        updatedBattleBoard.fields.first().enemy!!.statusEffects.keys shouldContainExactly setOf(ELECTRIFIED)
        updatedBattleBoard.fields.first().enemy!!.statusEffects[ELECTRIFIED] shouldBe 1
        updatedBattleBoard.fields.filterIndexed { index, _ -> index != 0 }.all { it.enemy!!.statusEffects == emptyMap<Effect, Int>() } shouldBe true
    }

}