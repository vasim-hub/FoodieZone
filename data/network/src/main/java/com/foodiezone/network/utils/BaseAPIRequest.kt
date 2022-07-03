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
package com.foodiezone.network.utils

import android.accounts.NetworkErrorException
import com.foodiezone.shared.*
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/*
*This is a base class which can be consider as tunnel for each HTTP request and response will go
* from this Although it can be generalize redirection of http response based on HTTP Code or
* also can be fetch api error message from
* response body and can be show to user
*
* */
abstract class BaseAPIRequest {
    suspend fun <T : Any> makeAPICall(call: suspend () -> Response<T>): T? {
        try {
            val response = call.invoke()
            when {
                response.code() == 200 -> {
                    return response.body()
                }
                response.code() == 400 -> {
                    throw NoDataFoundException("Fetch message from no data response")
                }
                response.code() == 401 -> {
                    throw UnAuthorizeAccessException("Fetch message from no data response")
                }
                response.code() == 404 -> {
                    throw NoResourceFoundException("No resource found")
                }
                response.code() == 500 -> {
                    throw SomethingWentWrongException("Fetch message from no data response")
                }
                else -> {
                    throw SomethingWentWrongException("Fetch message from no data response")
                }
            }
        } catch (e: Exception) {
            when (e) {
                is ConnectionShutdownException, is UnknownHostException -> {
                    throw NoInternetException("")
                    //We can make a new exception to show timeout for http request
                }
                is NoResourceFoundException -> {
                    throw NoInternetException("No resource found")
                }
                is SocketTimeoutException, is IOException, is NetworkErrorException -> {
                    throw NoInternetException("")
                }
                else -> {
                    throw SomethingWentWrongException("")
                }
            }
        }
    }
}


