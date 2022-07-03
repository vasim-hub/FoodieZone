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
package com.foodiezone.repositories.mappers

import com.foodiezone.cache.models.FoodieDataEntity
import com.foodiezone.domain.models.FoodieContent

internal fun FoodieDataEntity.toDomain(): FoodieContent {
    return FoodieContent(
        this.id,
        this.title,
        this.body,
        this.imageUrl,
    )
}

