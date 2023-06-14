package com.example.myapplication.infrastructure

import com.example.core.IItemRepository
import com.example.data.GrpcItemRepository
import com.example.data.InMemoryItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideChannel(): Channel {
        return ManagedChannelBuilder
            .forAddress("10.0.2.2", 8081)
            .usePlaintext()
            .build()
    }
    @Provides
    @Singleton
    fun provideItemRepository(channel: Channel): IItemRepository {
        return GrpcItemRepository(channel)
    }
}