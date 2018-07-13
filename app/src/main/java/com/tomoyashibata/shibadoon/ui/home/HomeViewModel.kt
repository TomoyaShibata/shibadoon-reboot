package com.tomoyashibata.shibadoon.ui.home

import android.arch.lifecycle.ViewModel
import com.tomoyashibata.shibadoon.ui.SingleLiveEvent

class HomeViewModel : ViewModel() {
  val onRequestLoginEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

  init {
    this.onRequestLoginEvent.call()
  }
}
