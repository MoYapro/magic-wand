package de.moyapro.colors.game.enemy.blueprints

import de.moyapro.colors.game.actions.fight.HitMageAction
import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.DirectionalImage
import de.moyapro.colors.game.model.ImageRef
import de.moyapro.colors.util.MAGE_III_ID
import de.moyapro.colors.util.MAGE_I_ID
import de.moyapro.colors.R

fun Grunt(): Enemy {
    val possibleActions = listOf(HitMageAction(MAGE_I_ID, 1), HitMageAction(MAGE_III_ID, 1), HitMageAction(MAGE_III_ID, 1))
    return Enemy(
        name = "Grunt",
        health = 10,
        breadth = 1,
        size = 1,
        possibleActions = possibleActions,
        statusEffects = emptyMap(),
        image = DirectionalImage(ImageRef(R.drawable.grunt_l), ImageRef(R.drawable.grunt_c), ImageRef(R.drawable.grunt_r))
    )

}