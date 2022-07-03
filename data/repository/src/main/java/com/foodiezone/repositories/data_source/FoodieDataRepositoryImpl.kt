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
package com.foodiezone.repositories.data_source

import com.foodiezone.cache.AppDatabase
import com.foodiezone.domain.models.FoodieContent
import com.foodiezone.domain.repository.FoodieDataRepository
import com.foodiezone.network.ApiService
import com.foodiezone.network.utils.BaseAPIRequest
import com.foodiezone.repositories.mappers.toDomain
import com.foodiezone.repositories.mappers.toEntity
import com.foodiezone.repositories.utils.NetworkHelper
import com.foodiezone.shared.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodieDataRepositoryImpl(
    private val apiService: ApiService,
    appDatabase: AppDatabase,
    private val networkHelper: NetworkHelper
) : BaseAPIRequest(), FoodieDataRepository {

    private val foodieDataDao = appDatabase.foodieDao()

    override suspend fun fetchFoodieListing(isRequiredRefreshData: Boolean): List<FoodieContent> {
        return withContext(Dispatchers.IO) {

            // Refreshing data
            if (isRequiredRefreshData) {
                foodieDataDao.deleteAllFoodieData()
            }

            if (!foodieDataDao.isDataExists()) {
                if (networkHelper.isNetworkConnected()) {
                    val listOfFoodieZone =
                        makeAPICall { apiService.getFoodieDataFromRemote() }?.map {
                            it.toEntity()
                        }
                    listOfFoodieZone?.let { foodieDataDao.saveFoodieData(foodieContentListing = it) }
                } else {
                    throw NoInternetException("")
                }
            }
            foodieDataDao.getFoodieData().map {
                it.toDomain()
            }
        }
    }
}