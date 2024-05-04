package de.moyapro.colors.wand.enemy

import de.moyapro.colors.game.enemy.*
import io.kotest.matchers.*
import org.junit.*

class EnemyGeneratorTest {

    @Test
    fun generateEnemy() {
        val enemyGenerator = EnemyGenerator(1)
        val enemy = enemyGenerator.generateEnemy()
        enemy shouldNotBe null
    }
}