package com.waracle.cakelist

import android.app.Application
import android.content.Context
import com.waracle.cakelist.di.networkModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class CakeApp : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<Context>("ApplicationContext") with singleton { this@CakeApp.applicationContext }
        import(networkModule)
    }
}