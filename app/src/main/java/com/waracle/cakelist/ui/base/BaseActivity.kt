package com.waracle.cakelist.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.waracle.cakelist.di.activityModule
import com.waracle.cakelist.di.viewModelModule
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
        bind<Context>("ActivityContext") with singleton { this@BaseActivity }
        import(activityModule)
        import(viewModelModule)
    }

    protected val viewModelFactory: ViewModelProvider.Factory by instance()
}