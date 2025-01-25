package de.moyapro.colors.game.functions

import de.moyapro.colors.game.model.FieldId

fun isInFrontRow(fieldId: FieldId) = fieldId.id >= 10
fun isInMiddleRow(fieldId: FieldId) = (5..9).contains(fieldId.id)
fun isInBackRow(fieldId: FieldId) = (0..4).contains(fieldId.id)