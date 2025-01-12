package de.moyapro.colors.game.enemy.blueprints

import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.enemy.actions.AttackMageEnemyAction
import de.moyapro.colors.game.model.DirectionalImage
import de.moyapro.colors.game.model.ImageRef
import de.moyapro.colors.R

fun Slime(): Enemy {
    val possibleActions = listOf(AttackMageEnemyAction("Smash"))
    return Enemy(
        name = "Slime",
        health = 5,
        breadth = 1,
        size = 1,
        possibleActions = possibleActions,
        statusEffects = emptyMap(),
        image = DirectionalImage(ImageRef(R.drawable.slime_l), ImageRef(R.drawable.slime_c), ImageRef(R.drawable.grunt_r))
    )

}