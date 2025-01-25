package de.moyapro.colors.game.model

import androidx.compose.ui.graphics.Color
import de.moyapro.colors.R

//◉ ○ ◌ ◍ ◎ ● ◐ ◑ ◒ ◓
enum class MagicType(val symbol: Char, val color: Color, val image: ImageRef) {
    SIMPLE('●', Color.Magenta, ImageRef(R.drawable.magic_generator_purple)),
    GREEN('●', Color.Green, ImageRef(R.drawable.magic_generator_green)),
    RED('●', Color.Red, ImageRef(R.drawable.magic_generator_red)),
    BLUE('●', Color.Blue, ImageRef(R.drawable.magic_generator_blue)),
    YELLOW('●', Color.Yellow, ImageRef(R.drawable.magic_generator_yellow)),
    NONE('●', Color.Black, ImageRef(R.drawable.magic_generator_rainbow)),
}
