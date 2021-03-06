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
package com.foodiezone.cache.converters

import androidx.room.TypeConverter
import com.foodiezone.cache.models.FoodieDataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FoodieEntityConverter {

    private val gson = Gson()

    @TypeConverter
    fun to(foodieEntityString: String?): List<FoodieDataEntity>? {
        if (foodieEntityString.isNullOrEmpty()) return null

        val type = object : TypeToken<List<FoodieDataEntity>?>() {}.type
        return gson.fromJson(foodieEntityString, type)
    }
}