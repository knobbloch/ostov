package com.knobblochsapplication.app.appcomponents.di

import android.app.Application
import com.knobblochsapplication.app.appcomponents.utility.Alerter
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * The application class which used to start koin for dependency injection
 */
class MyApp : Application() {

    lateinit var st: AppStorage
    lateinit var pr: PreferenceHelper

    override fun onCreate() {
        super.onCreate()
        instance = this
        st = AppStorage(this@MyApp).loadAll()
        pr = PreferenceHelper()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            loadKoinModules(getKoinModules())
        }
        Alerter.initialize(this)
    }

    /**
     * method which prepares [PreferenceHelper]s koin module
     * @return [Module] - the koin module
     */
    private fun preferenceModule(): Module {
        val prefsModule = module {
            single {
                pr
            }
        }
        return prefsModule
    }

    private fun storageModule(): Module {
        val storageModule = module {
            single {
                st
            }

        }
        return storageModule
    }

    /**
     * method which returns the list of koin module to register
     * @return MutableList<Module> - list of koin modules
     */
    private fun getKoinModules(): MutableList<Module> {
        val koinModules = mutableListOf<Module>()
        koinModules.add(preferenceModule()) //register preference module
        koinModules.add(storageModule())
        return koinModules
    }

    public companion object {

        // the application instance
        private lateinit var instance: MyApp

        /**
         * method to get instance of application object
         */
        public fun getInstance(): MyApp = instance
    }
}