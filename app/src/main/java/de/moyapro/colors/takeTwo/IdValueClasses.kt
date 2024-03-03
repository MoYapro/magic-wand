package de.moyapro.colors.takeTwo

import java.util.UUID

@JvmInline
value class WandId(val id: UUID = UUID.randomUUID())

@JvmInline
value class SlotId(val id: UUID = UUID.randomUUID())

@JvmInline
value class MagicId(val id: UUID = UUID.randomUUID())

@JvmInline
value class EnemyId(val id: UUID = UUID.randomUUID())
