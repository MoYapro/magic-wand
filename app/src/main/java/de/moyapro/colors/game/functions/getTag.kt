package de.moyapro.colors.game.functions

import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand

fun getTag(spell: Spell<*>?) = if (spell == null) "" else "${spell.id.id}_${spell.name}"

fun getTag(wand: Wand?) = if (wand == null) "" else "wand_${wand.id}_${wand.mageId}"