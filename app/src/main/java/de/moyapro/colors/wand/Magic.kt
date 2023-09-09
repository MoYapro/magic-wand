package de.moyapro.colors.wand

val NO_MAGIC: Magic = Magic(MagicType.NONE)
data class Magic(val type: MagicType = MagicType.SIMPLE) {
}
