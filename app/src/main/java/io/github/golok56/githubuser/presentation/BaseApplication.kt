package io.github.golok56.githubuser.presentation

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.Component

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}