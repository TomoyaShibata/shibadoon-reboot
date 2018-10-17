package com.tomoyashibata.shibadoon.model.network

import com.tomoyashibata.shibadoon.model.data.*
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.*

interface MastodonApi {
  // Accounts
  @GET("/api/v1/accounts/{id}")
  fun fetchAccount(@Path("id") id: String): Call<Account>

  @GET("/api/v1/accounts/verify_credentials")
  fun getCurrentAccount(): Call<Account>

  @POST("api/v1/apps")
  fun registerApp(@Body app: App): Deferred<Authentication>

  @POST("oauth/token")
  fun getToken(@Body requestAccessToken: RequestAccessToken): Deferred<AccessToken>

  // Notifications
  @GET("/api/v1/notifications")
  fun fetchNotification(
    @Query("max_id") maxId: Long? = null,
    @Query("since_id") sinceId: Long? = null,
    @Query("limit") limit: Int? = null,
    @Query("exclude_types") excludeTypes: Array<String>? = null
  ): Call<List<Notification>>

  // Timelines
  @GET("/api/v1/timelines/home")
  fun fetchHomeTimeline(
    @Query("local") local: Boolean? = null,
    @Query("only_media") onlyMedia: Boolean? = null,
    @Query("max_id") maxId: Long? = null,
    @Query("since_id") sinceId: Long? = null,
    @Query("limit") limit: Int? = null
  ): Call<List<Status>>

  // Statuses
  @POST("/api/v1/statuses/{id}/reblog")
  fun reblog(@Path("id") id: Long): Call<Status>

  @POST("/api/v1/statuses/{id}/unreblog")
  fun unreblog(@Path("id") id: Long): Call<Status>

  @POST("/api/v1/statuses")
  fun postStatus(@Body postStatus: PostStatus): Call<Status>

  // Streaming API
  // GET /api/v1/streaming/public
  @GET("/api/v1/streaming/public")
  fun streamingPublicStatuses()
}
