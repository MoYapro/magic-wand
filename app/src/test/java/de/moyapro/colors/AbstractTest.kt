package de.moyapro.colors

import androidx.compose.ui.test.junit4.*
import io.mockk.*
import org.junit.*
import org.junit.runner.*
import org.robolectric.*
import org.robolectric.annotation.*

@RunWith(RobolectricTestRunner::class)
@Config(
    application = TheApplication::class,
    packageName = "de.moyapro.colors",
    sdk = [28],
    instrumentedPackages = [
        "androidx.loader.content"
    ]
)
abstract class AbstractComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    open fun setup() {
        MockKAnnotations.init(this)
    }
}