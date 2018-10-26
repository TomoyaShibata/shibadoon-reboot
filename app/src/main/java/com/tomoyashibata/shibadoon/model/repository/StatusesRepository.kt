package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.model.data.PostStatus
import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.network.MastodonApi
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class StatusesRepository : KoinComponent {
  private val service: MastodonApi by inject(name = "currentAccount")

  fun fetchStatus(id: Long): Status {
    val result = this.service.fetchStatus(id).execute()
    return result.body() ?: throw  Exception(result.errorBody()?.string())
  }

  fun favourite(id: Long): Status {
    val result = this.service.favourite(id).execute()
    return result.body() ?: throw  Exception(result.errorBody()?.string())
  }

  fun unfavourite(id: Long): Status {
    val result = this.service.unfavourite(id).execute()
    return result.body() ?: throw  Exception(result.errorBody()?.string())
  }

  fun reblog(id: Long): Status {
    val result = this.service.reblog(id).execute()
    return result.body() ?: throw  Exception(result.errorBody()?.string())
  }

  fun unreblog(id: Long): Status {
    val result = this.service.unreblog(id).execute()
    return result.body() ?: throw  Exception(result.errorBody()?.string())
  }

  fun postStatus(postStatus: PostStatus): Status {
    val result = this.service.postStatus(postStatus).execute()
    return result.body() ?: throw Exception(result.errorBody()?.string())
  }
}
