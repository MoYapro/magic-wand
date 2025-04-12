package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.MagicType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MagicSlotsView() {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun singleMagicSlot() {
        val requiredMagic = Magic(type = MagicType.RED)
        val slots = listOf(MagicSlot(requiredMagic = requiredMagic))
        composeTestRule.setContent {
            MagicSlotsView(slots)
        }
        composeTestRule.onAllNodesWithTag("${requiredMagic.id}_Magic").assertCountEquals(1)
    }

    @Test
    fun twoMagicSlot() {
        val requiredMagic = listOf(Magic(type = MagicType.RED), Magic(type = MagicType.BLUE))
        val slots = requiredMagic.map { MagicSlot(requiredMagic = it) }
        composeTestRule.setContent {
            MagicSlotsView(slots)
        }
        composeTestRule.onAllNodesWithTag("${requiredMagic[0].id}_Magic").assertCountEquals(1)
        composeTestRule.onAllNodesWithTag("${requiredMagic[1].id}_Magic").assertCountEquals(1)
    }
}


