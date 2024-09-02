package de.moyapro.colors

import android.content.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.*
import org.robolectric.*
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class RoboTest {
    @Test
    fun clickingLogin_shouldStartLoginActivity() {
        Robolectric.buildActivity(FightActivity::class.java).use { controller ->
            controller.setup() // Moves the Activity to the RESUMED state
            val activity = controller.get()

            val expectedIntent = Intent(activity, FightActivity::class.java)
            val actual = shadowOf(RuntimeEnvironment.application).nextStartedActivity
            assertEquals(expectedIntent.component, actual.component)
        }
    }
}