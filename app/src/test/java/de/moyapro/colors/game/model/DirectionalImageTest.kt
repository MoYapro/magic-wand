package de.moyapro.colors.game.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Test

class DirectionalImageTest {
    @Test
    fun getSides() {
        val directionalImage = DirectionalImage(listOf(ImageRef(1), ImageRef(2), ImageRef(3)))
        directionalImage.left.imageRef shouldBe 1
        directionalImage.center.imageRef shouldBe 2
        directionalImage.right.imageRef shouldBe 3
    }

    @Test
    fun checkImageCount() {
        shouldThrow<IllegalArgumentException> { DirectionalImage(listOf(ImageRef(1))) }
    }

}