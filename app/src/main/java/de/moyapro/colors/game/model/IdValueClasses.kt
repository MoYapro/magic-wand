package de.moyapro.colors.game.model

import java.util.UUID

@JvmInline
value class WandId(val id: UUID = UUID.randomUUID())

@JvmInline
value class SlotId(val id: UUID = UUID.randomUUID())

@JvmInline
value class MagicId(val id: UUID = UUID.randomUUID())

@JvmInline
value class EnemyId(val id: UUID = UUID.randomUUID())

@JvmInline
value class MagicSlotId(val id: UUID = UUID.randomUUID())

@JvmInline
value class MageId(val id: Short = 0)

@JvmInline
value class SpellId(val id: UUID = UUID.randomUUID())

@JvmInline
value class FieldId(val id: Short = 0)
