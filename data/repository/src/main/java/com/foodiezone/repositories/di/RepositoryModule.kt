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
package com.foodiezone.repositories.di

import android.content.Context
import com.foodiezone.domain.repository.FoodieDataRepository
import com.foodiezone.repositories.data_source.FoodieDataRepositoryImpl
import com.foodiezone.repositories.utils.NetworkHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { provideNetworkHelper(androidContext()) }
    single { is24HoursFormat(androidContext()) }
    single<FoodieDataRepository> {
        FoodieDataRepositoryImpl(
            apiService = get(),
            appDatabase = get(),
            networkHelper = get()
        )
    }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun is24HoursFormat(context: Context) =
    android.text.format.DateFormat.is24HourFormat(context)
