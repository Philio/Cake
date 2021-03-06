package com.waracle.cakelist.di

import androidx.lifecycle.ViewModelProvider
import com.waracle.cakelist.util.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val activityModule = Kodein.Module("ActivityModule") {
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
}