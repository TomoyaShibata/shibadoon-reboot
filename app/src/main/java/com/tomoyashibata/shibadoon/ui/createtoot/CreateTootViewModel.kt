package com.tomoyashibata.shibadoon.ui.createtoot

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomoyashibata.shibadoon.model.usecase.CreateTootUseCase
import com.tomoyashibata.shibadoon.ui.SingleLiveEvent
import com.tomoyashibata.shibadoon.ui.async
import com.tomoyashibata.shibadoon.ui.ui

class CreateTootViewModel(
  private val createTootUseCase: CreateTootUseCase
) : ViewModel() {
  val content: MutableLiveData<String> = MutableLiveData()

  val onSuccessPostTootEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  fun onClickPostToot() {
    this.content.value?.let {
      ui {
        val status = async { this@CreateTootViewModel.createTootUseCase.execute(it) }.await()
        this@CreateTootViewModel.onSuccessPostTootEvent.call()
      }
    }
  }
}
