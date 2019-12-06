package by.sitko.restapp.api

import com.squareup.moshi.Json

data class AuthBody(
      @field:Json(name = "email") val email: String,
      @field:Json(name = "password") val password: String
)

data class LogOutBody(
      @field:Json(name = "session_id") val session_id: String
)

