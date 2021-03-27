package io.github.sentrymutablemaprepro

import android.os.Looper.getMainLooper
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class SentryProxyTest {

    @Test
    fun `should not throw UnsupportedOperationException when setting extras by mutable map`() {
        val proxy = SentryProxy(ApplicationProvider.getApplicationContext())

        proxy.captureEvent(mapOf("i am" to "mutable map").toMutableMap())

        assertTrue(
            proxy.statefulLogger.capturedExceptions.none { throwable ->
                throwable is java.lang.UnsupportedOperationException
            }
        )
    }

    @Test
    fun `should not throw UnsupportedOperationException when setting extras by immutable map`() {
        val proxy = SentryProxy(ApplicationProvider.getApplicationContext())

        proxy.captureEvent(mapOf("i am" to "immutable map"))

        shadowOf(getMainLooper()).idle()

        assertTrue(
            proxy.statefulLogger.capturedExceptions.none { throwable ->
                throwable is java.lang.UnsupportedOperationException
            }
        )
    }
}