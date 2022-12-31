package com.mad.e.twccomposeplanner.core.model

import kotlinx.datetime.Instant

/**
 * External data layer representation of an NiA episode
 */
data class Session(
    val id: String,
    val name: String,
    val publishDate: Instant,
    val alternateVideo: String?,
    val alternateAudio: String?,
    val sessions: List<Session>,
    val presenters: List<Presenter>
)
