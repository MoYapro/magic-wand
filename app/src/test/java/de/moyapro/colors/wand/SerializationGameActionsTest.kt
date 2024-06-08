package de.moyapro.colors.wand

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import io.kotest.matchers.equality.*
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
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
                TargetSelectedAction(target = EnemyId()),
                ShowTargetSelectionAction(originalAction = ZapAction(wandId = WandId())),
                HitAction(damage = Int.MAX_VALUE, targetMageId = MageId()),
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