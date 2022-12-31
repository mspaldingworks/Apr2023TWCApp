package com.mad.e.twccomposeplanner.core.model

/**
 * An [presenter] with the additional information for whether or not it is followed.
 */
data class FollowableAuthor(
    val presenter: Presenter,
    val isFollowed: Boolean
)
