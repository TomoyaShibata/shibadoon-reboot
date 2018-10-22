package com.tomoyashibata.shibadoon.ui.hometimeline

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyViewHolder
import com.tomoyashibata.shibadoon.itemTimelineBoostStatus
import com.tomoyashibata.shibadoon.itemTimelineFooter
import com.tomoyashibata.shibadoon.itemTimelineStatus
import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.ui.SingleLiveEvent
import java.util.*

class HomeTimelineController(
  private val data: ArrayList<Status>,
  private val loadingMore: Boolean = false,
  private val viewModel: HomeTimelineViewModel
) : EpoxyController() {
  val fetch: SingleLiveEvent<Unit> = SingleLiveEvent()

  override fun buildModels() {
    this.data.forEach {
      if (it.reblog == null) {
        itemTimelineStatus {
          id(it.id)
          viewModel(this@HomeTimelineController.viewModel)
          status(it)
        }

        return@forEach
      }

      itemTimelineBoostStatus {
        id(it.id)
        viewModel(this@HomeTimelineController.viewModel)
        status(it)
      }
    }

    itemTimelineFooter {
      id("loader")
      onBind { model, view, position ->
        this@HomeTimelineController.fetch.call()
      }
    }
  }

  override fun onModelBound(holder: EpoxyViewHolder, boundModel: EpoxyModel<*>, position: Int, previouslyBoundModel: EpoxyModel<*>?) {
    super.onModelBound(holder, boundModel, position, previouslyBoundModel)
    //Timber.i("itemId: ${holder.itemId}")
  }
}
