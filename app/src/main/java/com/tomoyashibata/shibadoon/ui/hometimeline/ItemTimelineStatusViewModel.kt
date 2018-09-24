package com.tomoyashibata.shibadoon.ui.hometimeline

import androidx.lifecycle.ViewModel
import com.tomoyashibata.shibadoon.model.data.Status
import com.tomoyashibata.shibadoon.model.usecase.ToggleStatusReblogUseCase
import com.tomoyashibata.shibadoon.ui.async
import com.tomoyashibata.shibadoon.ui.ui
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ItemTimelineStatusViewModel(
  val status: Status
) : ViewModel(), KoinComponent {
  private val toggleStatusReblogUseCase: ToggleStatusReblogUseCase by inject()

  fun onClickReblog() {
    ui {
      async { this@ItemTimelineStatusViewModel.toggleStatusReblogUseCase.execute(this@ItemTimelineStatusViewModel.status) }.await()
    }
  }
}
