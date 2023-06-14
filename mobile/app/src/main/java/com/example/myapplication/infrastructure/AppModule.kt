package com.example.myapplication.infrastructure

import com.example.core.IItemRepository
import com.example.data.InMemoryItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideItemRepository(): IItemRepository {
        return InMemoryItemRepository()
    }
}