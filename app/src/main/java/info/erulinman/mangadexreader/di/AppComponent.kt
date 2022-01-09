package info.erulinman.mangadexreader.di

import dagger.Component
import info.erulinman.mangadexreader.mangalist.MangaListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, BindModule::class])
interface AppComponent {

    fun inject(fragment: MangaListFragment)
}