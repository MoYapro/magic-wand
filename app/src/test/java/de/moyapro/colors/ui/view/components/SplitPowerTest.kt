package de.moyapro.colors.ui.view.components

import io.kotest.matchers.equals.shouldBeEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class SplitPowerTest {


    @Parameterized.Parameter(0)
    @JvmField
    var testdata: SplitTestData = SplitTestData(0, emptyList())


    private companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: Split power {0}")
        fun data() = listOf(
            SplitTestData(inputPower = 1, listOf(Pair(PowerMeterValues.ONE, 1))),
            SplitTestData(inputPower = 2, listOf(Pair(PowerMeterValues.ONE, 2))),
            SplitTestData(inputPower = 5, listOf(Pair(PowerMeterValues.FIVE, 1))),
            SplitTestData(inputPower = 6, listOf(Pair(PowerMeterValues.FIVE, 1), Pair(PowerMeterValues.ONE, 1))),
            SplitTestData(inputPower = 11, listOf(Pair(PowerMeterValues.TEN, 1), Pair(PowerMeterValues.ONE, 1))),
            SplitTestData(inputPower = 46, listOf(Pair(PowerMeterValues.TEN, 4), Pair(PowerMeterValues.FIVE, 1), Pair(PowerMeterValues.ONE, 1))),
            SplitTestData(inputPower = 446, listOf(Pair(PowerMeterValues.HUNDERED, 4), Pair(PowerMeterValues.TEN, 4), Pair(PowerMeterValues.FIVE, 1), Pair(PowerMeterValues.ONE, 1))),

            )
    }

    @Test
    fun splitPower() {
        val splits = splitPower(testdata.inputPower)
        splits shouldBeEqual testdata.output
        val sum = splits.fold(0) { acc, pair -> acc + (pair.first.value * pair.second) }
        sum shouldBeEqual testdata.inputPower
    }
}

data class SplitTestData(
    val inputPower: Int,
    val output: List<Pair<PowerMeterValues, Int>>,
)