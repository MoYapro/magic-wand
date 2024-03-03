package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.EnemyId

data class Enemy(
    val id: EnemyId = EnemyId(),
    val health: Int

)
