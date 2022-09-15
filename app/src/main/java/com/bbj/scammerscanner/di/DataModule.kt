package com.bbj.scammerscanner.di

import android.content.Context
import androidx.room.Room
import com.bbj.scammerscanner.data.DataRepositoryImpl
import com.bbj.scammerscanner.data.providers.CallLogsProvider
import com.bbj.scammerscanner.data.providers.SmsProvider
import com.bbj.scammerscanner.data.room.NumbersDAO
import com.bbj.scammerscanner.data.room.NumbersDatabase
import com.bbj.scammerscanner.domain.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCallLogsProvider(@ApplicationContext context: Context): CallLogsProvider {
        return CallLogsProvider(context)
    }

    @Provides
    @Singleton
    fun provideSmsProvider(@ApplicationContext context: Context): SmsProvider {
        return SmsProvider(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NumbersDatabase {
        return Room.databaseBuilder(
            context,
            NumbersDatabase::class.java,
            "numbersDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataRepository(
        callLogsProvider: CallLogsProvider,
        smsProvider: SmsProvider,
        numbersDAO: NumbersDAO
    ): DataRepository {
        return DataRepositoryImpl(callLogsProvider,smsProvider,numbersDAO)
    }

    @Provides
    @Singleton
    fun provideDAO(numbersDatabase: NumbersDatabase) : NumbersDAO{
        return numbersDatabase.getDao()
    }

}