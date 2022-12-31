package com.mad.e.dec2022twcapp.core.model

import com.mad.e.dec2022twcapp.core.data.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AppSettingsInteractor @Inject constructor(
    private val appRepository: AppRepository,
) {

    fun hasBeenOpened(): Flow<Boolean> {
        return appRepository.hasBeenOpened()
    }

    suspend fun appOpened() {
        appRepository.saveHasBeenOpenedPreference()
    }
}
