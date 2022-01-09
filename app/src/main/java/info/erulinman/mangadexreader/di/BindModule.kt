package info.erulinman.mangadexreader.di

import dagger.Binds
import dagger.Module
import info.erulinman.mangadexreader.api.RemoteRepository
import info.erulinman.mangadexreader.api.RemoteRepositoryImpl

@Module
interface BindModule {

    @Binds
    fun bindRemoteRepositoryImpl_to_RemoteRepository(
        repository: RemoteRepositoryImpl
    ): RemoteRepository
}