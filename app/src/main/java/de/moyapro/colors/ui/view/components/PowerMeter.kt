package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import de.moyapro.colors.util.ENEMY_SIZE

@Composable
@Preview
fun PowerMeter(
    @PreviewParameter(provider = PowerValuesProvider::class) power: Int,
    height: Int = ENEMY_SIZE,
) {
    val powerSplit = splitPower(power)
    Row {
        Spacer(modifier = Modifier.width(1.dp))
        Column(modifier = Modifier.height(height.dp), verticalArrangement = Arrangement.Bottom) {
            powerSplit.forEach {
                val (powerMeterValue, count) = it
                repeat(count) {
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height((2 * powerMeterValue.size).dp)
                            .background(Color.White)
                            .testTag("${powerMeterValue.value}Box")
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

fun splitPower(power: Int): List<Pair<PowerMeterValues, Int>> {
    val split = PowerMeterValues.values().fold(PowerMeterFold(power, emptyList())) { acc, powerMeterValue ->
        val count = acc.rest / powerMeterValue.value
        val newRest = acc.rest % powerMeterValue.value
        if (count == 0) return@fold acc
        else PowerMeterFold(newRest, acc.acc + Pair(powerMeterValue, count))

    }
    assert(split.rest == 0) { "failed to split power! There is a rest: ${split.rest}" }
    return split.acc
}

private data class PowerMeterFold(val rest: Int, val acc: List<Pair<PowerMeterValues, Int>>)

class PowerValuesProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int>
        get() = sequenceOf(1, 2, 3, 6, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946)

}

enum class PowerMeterValues(val value: Int, val size: Int) {
    THOUSANDsS(10000, 8),
    THOUSANDs(1000, 7),
    THOUSAND(500, 6),
    HUNDERED(100, 5),
    FIFTY(50, 4),
    TEN(10, 3),
    FIVE(5, 2),
    ONE(1, 1),
}