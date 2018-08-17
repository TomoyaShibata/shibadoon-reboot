package com.tomoyashibata.shibadoon.model.network

import com.tomoyashibata.shibadoon.model.data.*
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
}
