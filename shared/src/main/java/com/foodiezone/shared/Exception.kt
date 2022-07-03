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
package com.foodiezone.shared

import java.io.IOException

class NoInternetException(message: String) : IOException(message)

class SomethingWentWrongException(message: String) : IOException(message)

class UnAuthorizeAccessException(message: String) : IOException(message)

class NoDataFoundException(message: String) : IOException(message)

class NoResourceFoundException(message: String) : IOException(message)