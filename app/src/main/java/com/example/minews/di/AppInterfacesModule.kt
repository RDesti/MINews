package com.example.minews.di

import com.example.minews.requesters.IRedditDataRequester
import com.example.minews.requesters.RedditDataRequester
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInterfacesModule {
    @Binds
    abstract fun bindRedditDataRequester(impl: RedditDataRequester): IRedditDataRequester

}