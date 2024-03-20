package de.moyapro.colors.wand

import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMagic
import de.moyapro.colors.game.actions.EndTurnAction
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.GiveWandAction
import de.moyapro.colors.game.actions.HitAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.game.actions.PlaceMagicAction
import de.moyapro.colors.game.actions.ShowTargetSelectionAction
import de.moyapro.colors.game.actions.TargetSelectedAction
import de.moyapro.colors.game.actions.ZapAction
import de.moyapro.colors.takeTwo.MageId
import de.moyapro.colors.takeTwo.SlotId
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.getConfiguredJson
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class SerializationGameActionsTest(private val value: Any) {

    companion object {
        @Parameters(name = "{index}: {0}")
        @JvmStatic
        fun data(): List<GameAction> {
            return listOf(
                PlaceMagicAction(
                    magicToPlace = createExampleMagic(),
                    slotId = SlotId(),
                    wandId = WandId()
                ),
                ZapAction(WandId()),
                NoOp(),
                EndTurnAction(),
                TargetSelectedAction(target = createExampleEnemy()),
                ShowTargetSelectionAction(originalAction = ZapAction(wandId = WandId())),
                HitAction(damage = Int.MAX_VALUE, target = MageId()),
                GiveWandAction(mageId = MageId(), wandId = WandId()),
            )
        }
    }

    @Test
    fun `de-serialize single types`() {
        val objectMapper = getConfiguredJson()
        val internalValue: GameAction = value as GameAction
        val json = objectMapper.writeValueAsString(internalValue)
        val deserialized: GameAction = objectMapper.readValue(json, GameAction::class.java)
        deserialized shouldBeEqualToComparingFields internalValue
    }
}