package com.yanivsos.mixological.app_initializers

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

class TimberInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        print("TimberInitializer on create")
        Timber.plant(Timber.DebugTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}