package de.moyapro.colors.game.model

import androidx.compose.ui.graphics.*

//◉ ○ ◌ ◍ ◎ ● ◐ ◑ ◒ ◓
enum class MagicType(val symbol: Char, val color: Color) {
    SIMPLE('●', Color.Magenta),
    GREEN('●', Color.Green),
    RED('●', Color.Red),
    BLUE('●', Color.Blue),
    NONE('●', Color.Black)
}
