package de.moyapro.colors.takeTwo

data class Mage(override val id: MageId = MageId(), val health: Int): HasId<MageId>