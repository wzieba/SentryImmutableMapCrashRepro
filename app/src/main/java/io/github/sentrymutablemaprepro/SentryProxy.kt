package io.github.sentrymutablemaprepro

import android.content.Context
import io.sentry.*
import io.sentry.android.core.SentryAndroid

class SentryProxy(context: Context) {

    val statefulLogger = StatefulLogger()

    init {
        SentryAndroid.init(context) { options ->
            options.apply {
                dsn = "https://public@sentry.example.com/1"
                setDebug(true)
                setLogger(statefulLogger)
            }
        }
    }

    fun captureEvent(eventExtras: Map<String, String>) {
        Sentry.setExtra("some", "extra")

        Sentry.captureEvent(
            SentryEvent().apply {
                setExtras(eventExtras)
            })
    }

    inner class StatefulLogger(private val systemOutLogger: SystemOutLogger = SystemOutLogger()) :
        ILogger by systemOutLogger {

        val capturedExceptions = mutableListOf<Throwable>()

        override fun log(level: SentryLevel, message: String, throwable: Throwable) {
            capturedExceptions.add(throwable)
            systemOutLogger.log(level, message, throwable)
        }
    }
}
