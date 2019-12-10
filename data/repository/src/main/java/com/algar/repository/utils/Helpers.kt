package com.algar.repository.utils

import com.algar.model.dataOutdatedThreshold
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit

object Helpers {

    /**
     * We consider data to be outdated when the last time we fetched it was more than
     * [dataOutdatedThreshold] ago.
     */
    fun shouldRefreshFromNetwork(
        lastRefreshed: DateTime,
        currentTime: DateTime = DateTime.now()
    ): Boolean {
        val timeSinceRefresh = currentTime.millis - lastRefreshed.millis
        val timeSinceLastRefreshInMinutes = TimeUnit.MILLISECONDS.toMinutes(timeSinceRefresh)
        return timeSinceLastRefreshInMinutes >= dataOutdatedThreshold
    }
}