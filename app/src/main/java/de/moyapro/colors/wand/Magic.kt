package de.moyapro.colors.wand

import de.moyapro.colors.takeTwo.MagicId
import java.util.UUID

val NO_MAGIC: Magic =
    Magic(MagicId(UUID.fromString("00000000-0000-0000-0000-000000000000")), MagicType.NONE)

data class Magic(
    val id: MagicId = MagicId(),
    val type: MagicType = MagicType.SIMPLE,
)
