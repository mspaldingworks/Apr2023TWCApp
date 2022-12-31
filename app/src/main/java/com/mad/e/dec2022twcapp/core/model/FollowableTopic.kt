package com.mad.e.twccomposeplanner.core.model

import com.mad.e.dec2022twcapp.core.model.Topic


/**
 * A [topic] with the additional information for whether or not it is followed.
 */
data class FollowableTopic(
    val topic: Topic,
    val isFollowed: Boolean
)
