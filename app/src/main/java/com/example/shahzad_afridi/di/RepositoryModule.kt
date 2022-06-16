package com.example.shahzad_afridi.di

import com.example.shahzad_afridi.data.repository.BlogRepository
import com.example.shahzad_afridi.data.repository.BlogRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(): BlogRepository {
        return BlogRepositoryImp()
    }

}