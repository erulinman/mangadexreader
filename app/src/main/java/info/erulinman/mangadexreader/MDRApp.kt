package info.erulinman.mangadexreader

import android.app.Application
import info.erulinman.mangadexreader.api.RemoteRepository
import info.erulinman.mangadexreader.api.RemoteRepositoryImpl

class MDRApp : Application() {
    val repository: RemoteRepositoryImpl by lazy { RemoteRepositoryImpl() }
}