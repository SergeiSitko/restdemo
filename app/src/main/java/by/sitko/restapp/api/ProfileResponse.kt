package by.sitko.restapp.api

import com.squareup.moshi.Json

data class ProfileResponse(@field:Json(name = "account") val info: Info)

data class Info(
      @field:Json(name = "account") val account: Account,
      @field:Json(name = "profiles") val profiles: List<Profile>,
      @field:Json(name = "session") val session: String?
)

data class Profile(
      @field:Json(name = "account_id") val account_id: String,
      @field:Json(name = "avatar_url") val avatar_url: String,
      @field:Json(name = "email") val email: String,
      @field:Json(name = "first_name") val first_name: String,
      @field:Json(name = "gender") val gender: Any,
      @field:Json(name = "joined_at") val joined_at: String,
      @field:Json(name = "kyc_verified") val kyc_verified: Boolean,
      @field:Json(name = "langs_spoken_names") val langs_spoken_names: List<Any>,
      @field:Json(name = "last_name") val last_name: String,
      @field:Json(name = "location") val location: String,
      @field:Json(name = "phone_country") val phone_country: Any,
      @field:Json(name = "phone_number") val phone_number: Any,
      @field:Json(name = "profile_id") val profile_id: String,
      @field:Json(name = "profile_type") val profile_type: String
)

data class Account(
      @field:Json(name = "2fa_method") val `2fa_method`: Any,
      @field:Json(name = "account_id") val account_id: String,
      @field:Json(name = "account_type") val account_type: String,
      @field:Json(name = "created_at") val created_at: String,
      @field:Json(name = "email") val email: String,
      @field:Json(name = "email_verified") val email_verified: Boolean,
      @field:Json(name = "password") val password: String,
      @field:Json(name = "phone") val phone: String,
      @field:Json(name = "totp_verified") val totp_verified: Boolean
)