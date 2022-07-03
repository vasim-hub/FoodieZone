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
package com.foodiezone.base

import androidx.fragment.app.Fragment
import com.foodiezone.R
import com.foodiezone.shared.NoDataFoundException
import com.foodiezone.shared.NoInternetException
import com.foodiezone.shared.NoResourceFoundException
import com.foodiezone.shared.UnAuthorizeAccessException
import com.foodiezone.utils.showSnackBar

open class BaseFragment(id: Int) : Fragment(id) {
    /*
    * This method is use to show loading in whole app like in dialog
    * or can be override in particular screen if needs to change dialog behaviour
    * */
    open fun showLoading() {}

    /*
    * This method is used to hide progress or loader whenever http request finished.
    * */
    open fun hideLoading() {}

    /*
    * This method is used to handle error type of http based on custom type of exception
    * */
    open fun showError(exception: Exception) {
        when (exception) {
            is NoInternetException -> (onNoInternetConnection())
            is NoDataFoundException -> (onNoDataFound())
            is UnAuthorizeAccessException -> (onAuthorizationExpired())
            is NoResourceFoundException -> (onNoResourceFound(exception.message))
            else -> {
                onSomethingWentWrong()
            }
        }
    }

    /* This method is used to handle error type of http error or any general error  */
    private fun onSomethingWentWrong() {
        requireActivity().showSnackBar(getString(R.string.something_went_wrong))
    }

    /* This method is used to handle error http 401 for session expired */
    private fun onAuthorizationExpired() {
    }

    /* This method is used to handle error http 404 for no resource found */
    private fun onNoResourceFound(message: String?) {
        message?.let { requireActivity().showSnackBar(it) }
    }

    /* This method is used to handle no internet connection */
    private fun onNoInternetConnection() {
        requireActivity().showSnackBar(getString(R.string.no_internet_description))
    }

    /* This method is used to handle no data */
    private fun onNoDataFound() {
    }
}