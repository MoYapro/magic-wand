package de.moyapro.colors.wand

val NO_MAGIC: Magic = Magic(MagicType.NONE)

data class Magic(val type: MagicType = MagicType.SIMPLE) {
    fun getValue(): Int {
        return when (this.type) {
            MagicType.NONE -> 0
            MagicType.SIMPLE -> 1
            MagicType.GREEN -> 2
        }
    }
}
