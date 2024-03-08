package de.moyapro.colors.wand

import androidx.compose.ui.graphics.Color

enum class MagicType(val symbol: Char, val color: Color) {
    SIMPLE('❂', Color.Magenta),
    GREEN('◕', Color.Green),
    NONE('X', Color.Black)
}
