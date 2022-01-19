package info.erulinman.mangadexreader.di

import dagger.Binds
import dagger.Module
import info.erulinman.mangadexreader.api.MangaRepository
import info.erulinman.mangadexreader.api.MangaRepositoryImpl

@Module
interface BindModule {

    @Binds
    fun bindMangaRepositoryImpl_to_MangaRepository(
        mangaRepository: MangaRepositoryImpl
    ): MangaRepository
}