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
package com.foodiezone.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodiezone.base.ResponseHandler
import com.foodiezone.domain.CoroutineDispatcherProvider
import com.foodiezone.domain.models.FoodieContent
import com.foodiezone.domain.usecase.GetFoodieListingUserCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodieListingViewModel(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getFoodieListingUserCase: GetFoodieListingUserCase,
) : ViewModel() {

    private val _foodieContentListing =
        MutableStateFlow<ResponseHandler<List<FoodieContent>>>(ResponseHandler.Loading)
    val foodieContentListing: StateFlow<ResponseHandler<List<FoodieContent>>> =
        _foodieContentListing

    init {
        fetchFoodieListing(isRequiredToRefreshData = false)
    }

    fun fetchFoodieListing(isRequiredToRefreshData: Boolean) {
        _foodieContentListing.value = ResponseHandler.Loading

        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val foodiesResponseList = getFoodieListingUserCase.execute(isRequiredToRefreshData)
                _foodieContentListing.value = ResponseHandler.OnSuccessResponse(foodiesResponseList)

            } catch (e: Exception) {
                _foodieContentListing.value = ResponseHandler.OnFailed(e)
            }
        }
    }
}