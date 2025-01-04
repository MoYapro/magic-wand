package de.moyapro.colors.game.model

@JvmInline
value class ImageRef(val imageRef: Int)

data class DirectionalImage(val imageRefs: List<ImageRef>) {
    constructor(left: ImageRef, center: ImageRef, right: ImageRef) : this(listOf(left, center, right))

    init {
        require(imageRefs.size == 3) { "DirectedImage must have three images" }
    }


    val left: ImageRef = imageRefs[0]
    val center: ImageRef = imageRefs[1]
    val right: ImageRef = imageRefs[2]
}