package de.moyapro.colors.game.model

import androidx.compose.ui.graphics.*

//◉ ○ ◌ ◍ ◎ ● ◐ ◑ ◒ ◓
enum class MagicType(val symbol: Char, val color: Color) {
    SIMPLE('●', Color.Magenta),
    GREEN('●', Color.Green),
    NONE('●', Color.Black)
}
