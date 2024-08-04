package de.moyapro.colors

import android.view.*
import androidx.lifecycle.*
import androidx.test.ext.junit.rules.*
import androidx.test.ext.junit.runners.*
import androidx.test.platform.app.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("de.moyapro.colors", appContext.packageName)
    }

    @Test
    fun testEvent() {
        val scenario = activityScenarioRule.scenario
        scenario.onActivity { activity ->
            scenario.moveToState(Lifecycle.State.CREATED)
            activity.findViewById<View>(10000).isActivated
        }
    }

    @Test
    fun reloadEverythingAfterRecreate() {
        val scenario = activityScenarioRule.scenario
        scenario.onActivity { activity ->
            scenario.recreate() // recreate activity after scheduler removed it from memory
        }
    }
}