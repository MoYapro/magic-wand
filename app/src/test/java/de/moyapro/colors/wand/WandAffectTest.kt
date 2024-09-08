package de.moyapro.colors.wand

import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import io.kotest.matchers.*
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

}