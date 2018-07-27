package com.tomoyashibata.shibadoon.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tomoyashibata.shibadoon.model.usecase.HasSavedTokenUseCase
import com.tomoyashibata.shibadoon.ui.SingleLiveEvent
import com.tomoyashibata.shibadoon.ui.async
import com.tomoyashibata.shibadoon.ui.ui
import timber.log.Timber

class HomeViewModel(
  private val hasSavedTokenUseCase: HasSavedTokenUseCase
) : ViewModel() {
  val huga: MutableLiveData<String> = MutableLiveData()
  val onRequestLoginEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

  init {
    this@HomeViewModel.onRequestLoginEvent.call()

//    ui {
//      val hasSavedToken = async { this@HomeViewModel.hasSavedTokenUseCase.execute() }.await()
//      if (!hasSavedToken) {
//        this@HomeViewModel.onRequestLoginEvent.call()
//      } else {
//        Timber.i("あるよ")
//        this@HomeViewModel.huga.value = "ふがwww"
//      }
//    }
  }
}
