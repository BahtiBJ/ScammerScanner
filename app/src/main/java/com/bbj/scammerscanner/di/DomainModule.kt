package com.bbj.scammerscanner.di

import com.bbj.scammerscanner.data.DataRepositoryImpl
import com.bbj.scammerscanner.data.providers.CallLogsProvider
import com.bbj.scammerscanner.data.providers.SmsProvider
import com.bbj.scammerscanner.data.room.NumbersDAO
import com.bbj.scammerscanner.domain.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideDataRepository(
        callLogsProvider: CallLogsProvider,
        smsProvider: SmsProvider,
        numbersDAO: NumbersDAO
    ): DataRepository {
        return DataRepositoryImpl(callLogsProvider,smsProvider,numbersDAO)
    }

}