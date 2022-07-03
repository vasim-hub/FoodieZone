/*
Copyright 2022 Vasim Mansuri

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.foodiezone

import android.app.Application
import com.foodiezone.cache.di.cacheModule
import com.foodiezone.di.coroutineDispatcherProviderModule
import com.foodiezone.di.useCaseModules
import com.foodiezone.network.di.networkModule
import com.foodiezone.repositories.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FoodieZoneApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@FoodieZoneApplication)
            val modules = listOf(
                networkModule, cacheModule, repositoryModule, useCaseModules,
                coroutineDispatcherProviderModule
            )
            modules(modules)
        }
    }
}