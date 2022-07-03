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
package com.foodiezone.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import coil.load
import com.foodiezone.R
import com.google.android.material.snackbar.Snackbar

/* Global method for showing image */
fun ImageView.loadImage(imageUrl: String) {
    load(imageUrl) {
        placeholder(R.drawable.splash)
        error(R.drawable.noimage)
    }
}


//Common method for showing SnackBar
fun Activity.showSnackBar(
    message: String, action: String? = null,
    actionListener: View.OnClickListener? = null, duration: Int = Snackbar.LENGTH_SHORT
) {
    val snackBar = Snackbar.make(this.findViewById(android.R.id.content), message, duration)
        .setTextColor(Color.BLACK)
    if (action != null && actionListener != null) {
        snackBar.setAction(action, actionListener)
    }
    snackBar.show()
}