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
package com.foodiezone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foodiezone.databinding.ItemFoodieBinding
import com.foodiezone.domain.models.FoodieContent
import com.foodiezone.utils.loadImage

class FoodieContentListingAdapter :
    RecyclerView.Adapter<FoodieContentListingAdapterRecyclerViewHolder>() {

    var showFoodieList: List<FoodieContent> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodieContentListingAdapterRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodieBinding.inflate(layoutInflater, parent, false)
        return FoodieContentListingAdapterRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FoodieContentListingAdapterRecyclerViewHolder,
        position: Int
    ) {
        holder.itemView.context
        val foodie = showFoodieList[position]
        holder.bind(foodie)
    }

    override fun getItemCount() = showFoodieList.size
}

class FoodieContentListingAdapterRecyclerViewHolder(private val binding: ItemFoodieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(foodie: FoodieContent) {
        binding.apply {
            imageViewFoodie.loadImage(foodie.imageUrl)
            txTitle.text = foodie.title
            txDescription.text = foodie.body
        }
    }
}