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
package com.foodiezone.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.foodiezone.cache.models.FoodieDataEntity
import com.foodiezone.cache.utils.TableConstantsName.TABLE_NAME_FOODIE_DATA

@Dao
interface FoodieDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveFoodieData(foodieContentListing: List<FoodieDataEntity>)

    @Query("DELETE FROM $TABLE_NAME_FOODIE_DATA")
    fun deleteAllFoodieData()

    @Query("SELECT * FROM $TABLE_NAME_FOODIE_DATA")
    fun getFoodieData(): List<FoodieDataEntity>

    @Query("SELECT EXISTS(SELECT * FROM $TABLE_NAME_FOODIE_DATA)")
    fun isDataExists(): Boolean
}