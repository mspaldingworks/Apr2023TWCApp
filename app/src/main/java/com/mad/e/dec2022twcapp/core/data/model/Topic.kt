package com.mad.e.twccomposeplanner.core.data.model

import com.mad.e.twccomposeplanner.core.database.model.TopicEntity
import com.mad.e.twccomposeplanner.network.model.NetworkTopic

fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl
)
