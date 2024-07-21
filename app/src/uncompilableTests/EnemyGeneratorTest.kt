package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.enemy.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

class EnemyGeneratorTest {

    @Test
    fun generateEnemy() {
        val enemyGenerator = EnemyGenerator(seed = 1, level = 0)
        val enemy1 = enemyGenerator.generateEnemy()
        enemy1 shouldNotBe null
        enemy1.possibleActions shouldHaveSize 1
        enemy1

        val enemy2 = enemyGenerator.generateEnemy()
        enemy2 shouldNotBe enemy1
    }
}