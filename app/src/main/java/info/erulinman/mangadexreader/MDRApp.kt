package info.erulinman.mangadexreader

import android.app.Application
import info.erulinman.mangadexreader.model.Repository

class MDRApp : Application() {
    val repository: Repository by lazy { Repository() }
}