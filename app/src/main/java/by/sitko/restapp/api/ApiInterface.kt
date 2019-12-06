package by.sitko.restapp.api

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val API_KEY = "bJHiLNfwe864HJMrmMtVJtv34"

interface ApiInterface {

    @POST("accounts/auth")
    fun auth(@Body authBody: AuthBody): Deferred<LoginResponse>

    @POST("sessions/refresh")
    fun refreshToken(): Deferred<LoginResponse>

    @GET("accounts/current")
    fun getProfile(): Deferred<ProfileResponse>

    @POST("/accounts/sessions/end")
    fun logOut(@Body logOutBody: LogOutBody): Deferred<Unit>


    /*@GET("/picker_api/getProduct")
    fun getProduct(
          @Query("size_id") sizeId: String,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<ProductResponse>

    @GET("/picker_api/getOrder")
    fun getOrder(
          @Query("order_id") orderId: String,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<OrderResponse>

    @GET("/picker_api/getListOrders")
    fun getListOrders(
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<OrderListResponse>

    @GET("/picker_api/getPlaceProducts")
    fun getPlaceProducts(
          @Query("place") place: String,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<BaseResponse<ShelfProductData>>

    @POST("/picker_api/setPlace")
    fun setPlace(
          @Body request: PlaceRequest,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<ResultResponse>

    @POST("/picker_api/setStatusReady")
    fun setStatusReady(
          @Body request: ReadyStatusBody,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<ResultResponse>

    @POST("/picker_api/setStatusNotFound")
    fun setStatusNotFound(
          @Body request: NotFoundStatusRequest,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<ResultResponse>

    @POST("/picker_api/setStatusCancel")
    fun setStatusCancel(
          @Body request: CancelRequest,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<ResultResponse>

    @POST("/picker_api/setPlaceReturn")
    fun setPlaceReturn(
          @Body request: PlaceReturnRequest,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<BaseResponse<Any>>

    @POST("/picker_api/setNewPlace")
    fun setNewPlace(
          @Body request: NewPlaceBody,
          @Query("api_key") accessToken: String = API_KEY
    ): Deferred<BaseResponse<Any>>*/
}