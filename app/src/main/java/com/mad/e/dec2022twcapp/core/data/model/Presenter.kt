package com.mad.e.dec2022twcapp.core.data.model


fun NetworkPresenter.asEntity() = PresenterEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    twitter = twitter,
    mediumPage = mediumPage,
    bio = bio,
)
