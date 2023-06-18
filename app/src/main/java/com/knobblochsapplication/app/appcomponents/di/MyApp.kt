package com.knobblochsapplication.app.appcomponents.di

import android.app.Application
import android.content.SharedPreferences
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


    override fun onCreate(): Unit {
        super.onCreate()
        instance = this
        pref = getSharedPreferences("id", MODE_PRIVATE)
        if (pref.getInt("id", -1)==-1) {
            put(1)
        }

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            loadKoinModules(getKoinModules())
        }
    }

    /**
     * method which prepares [PreferenceHelper]s koin module
     * @return [Module] - the koin module
     */
    private fun preferenceModule(): Module {
        val prefsModule = module {
            single {
                PreferenceHelper()
            }
        }
        return prefsModule
    }

    /**
     * method which returns the list of koin module to register
     * @return MutableList<Module> - list of koin modules
     */
    private fun getKoinModules(): MutableList<Module> {
        val koinModules = mutableListOf<Module>()
        koinModules.add(preferenceModule()) //register preference module
        return koinModules
    }

    companion object {

        // the application instance
        lateinit var instance: MyApp

        lateinit var pref: SharedPreferences



        /**
         * method to get instance of application object
         */
        @JvmName("getInstance1")
        fun getInstance(): MyApp = instance
    }

    fun put(inter: Int) {
        val edit: SharedPreferences.Editor = pref.edit()
        edit.putInt("id", inter)
        edit.apply();
    }
}