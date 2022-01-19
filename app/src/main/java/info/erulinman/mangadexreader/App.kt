package info.erulinman.mangadexreader

import android.app.Application
import info.erulinman.mangadexreader.di.AppComponent
import info.erulinman.mangadexreader.di.DaggerAppComponent

class App : Application() {

    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent
        get() {
            checkNotNull(_appComponent)
            return _appComponent as AppComponent
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.create()
    }
}