package com.tomoyashibata.shibadoon.ui.hometimeline

import com.airbnb.epoxy.EpoxyController
import com.tomoyashibata.shibadoon.itemTimelineBoostStatus
import com.tomoyashibata.shibadoon.itemTimelineStatus
import com.tomoyashibata.shibadoon.model.data.Status
import java.util.*

class HomeTimelineController(
  private val data: ArrayList<Status>,
  private val loadingMore: Boolean = false
) : EpoxyController() {
  override fun buildModels() {
    this.data.forEach {
      if (it.reblog == null) {
        itemTimelineStatus {
          id(it.id)
          status(it)
        }

        return@forEach
      }

      itemTimelineBoostStatus {
        id(it.id)
        status(it)
      }
    }
  }
}
