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
package com.foodiezone.di

import com.foodiezone.domain.CoroutineDispatcherProvider
import com.foodiezone.domain.usecase.GetFoodieListingUserCase
import com.foodiezone.ui.viewmodel.FoodieListingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val useCaseModules = module {
    single { GetFoodieListingUserCase(get()) }
}
val coroutineDispatcherProviderModule = module {
    single { CoroutineDispatcherProvider() }
}

val homeModule = module {
    viewModel { FoodieListingViewModel(get(), get()) }
}

val loadHomeModule by lazy {
    val modules = listOf(homeModule)
    loadKoinModules(modules)
}