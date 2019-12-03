package by.sitko.restapp.api

import com.squareup.moshi.Json

abstract class BaseApiResponse {
    var status: Int = 0
    var message: String? = null
}


const val notFound = "404"

/**
 * Abstraction over a server response.
 *
 * @param status is the same server's status code and it has to be always
 * @param data the result is coming and it's optional
 * @param error the error  is coming and it's optional
 */
class BaseResponse<R>(
      @field:Json(name = "status") val status: Int,
      @field:Json(name = "data") val data: R?,
      @field:Json(name = "error") val error: String?
) {
    fun isError() = data == null && error != null
}

class ErrorData(val status: String, val error: Error?)

class Error(val message: String)

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

data class LoginResponse(val token: String)
