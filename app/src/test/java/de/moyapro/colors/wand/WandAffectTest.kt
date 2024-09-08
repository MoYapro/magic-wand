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
        val battleBoard = createExampleBattleBoard(TargetDummy(10))
        val spell = Bonk()
        val wand = createExampleWand(MAGE_I_ID, spell)

        val affectedFieldId = battleBoard.fields.first().id
        val updatedBattleBoard = wand.affect(battleBoard, affectedFieldId)

        updatedBattleBoard.fields.findById(affectedFieldId)?.enemy?.health shouldBe 9
    }

}