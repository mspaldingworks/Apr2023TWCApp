package com.mad.e.dec2022twcapp.core.data.repository

import com.mad.e.twccomposeplanner.core.model.Presenter
import kotlinx.coroutines.flow.Flow

interface PresentersRepository : Syncable {
    /**
     * Gets the available Authors as a stream
     */
    fun getPresentersStream(): Flow<List<Presenter>>

    /**
     * Gets data for a specific author
     */
    fun getPresenterStream(id: String): Flow<Presenter>

    /**
     * Sets the user's currently followed authors
     */
    suspend fun setFollowedPresenterIds(followedPresenterIds: Set<String>)

    /**
     * Toggles the user's newly followed/unfollowed author
     */
    suspend fun toggleFollowedPresenterId(followedPresenterId: String, followed: Boolean)

    /**
     * Returns the users currently followed authors
     */
    fun getFollowedPresenterIdsStream(): Flow<Set<String>>
}
