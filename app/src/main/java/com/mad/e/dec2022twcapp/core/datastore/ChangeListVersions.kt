package com.mad.e.dec2022twcapp.core.datastore

/**
 * Class summarizing the local version of each model for sync
 */
data class ChangeListVersions(
    val topicVersion: Int = -1,
    val presenterVersion: Int = -1,
    val sessionVersion: Int = -1,
)
