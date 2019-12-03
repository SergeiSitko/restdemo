package by.sitko.restapp.api

import com.squareup.moshi.Json

data class AuthRequest(
      @field:Json(name = "email") val email: String,
      @field:Json(name = "password") val password: String
)

