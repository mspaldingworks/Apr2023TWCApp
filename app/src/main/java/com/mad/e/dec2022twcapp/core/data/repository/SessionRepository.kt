package com.mad.e.dec2022twcapp.core.data.repository

import com.mad.e.twccomposeplanner.core.model.Session
import kotlinx.coroutines.flow.Flow

/**
 * Data layer implementation for [Session]
 */
interface SessionRepository : Syncable {
    /**
     * Returns available news resources as a stream.
     */
    fun getSessionsStream(): Flow<List<Session>>

    /**
     * Returns available news resources as a stream filtered by authors or topics.
     */
    fun getSessionsStream(
        filterAuthorIds: Set<String> = emptySet(),
        filterTopicIds: Set<String> = emptySet(),
    ): Flow<List<Session>>
}
