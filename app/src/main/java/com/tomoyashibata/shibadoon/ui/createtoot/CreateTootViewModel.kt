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
  val remainTootContentCount: MutableLiveData<String> = MutableLiveData()
  val postTootButtonEnabled: MutableLiveData<Boolean> = MutableLiveData()

  init {
    this.calculateRemainTootContentCount()
    this.postTootButtonEnabled.value = false
  }

  fun calculateRemainTootContentCount() {
    val remainTootContentCount = 500 - (this.content.value?.length ?: 0)
    this.postTootButtonEnabled.value = remainTootContentCount >= 0
    this.remainTootContentCount.value = remainTootContentCount.toString()
  }

  val onSuccessPostTootEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  val onErrorPostTootEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
  fun onClickPostToot() {
    this.postTootButtonEnabled.value = true

    this.content.value?.let {
      ui {
        try {
          val status = async { this@CreateTootViewModel.createTootUseCase.execute(it) }.await()
          this@CreateTootViewModel.onSuccessPostTootEvent.call()
        } catch (e: Exception) {
          this@CreateTootViewModel.onErrorPostTootEvent.call()
        } finally {
          this@CreateTootViewModel.postTootButtonEnabled.value = false
        }
      }
    }
  }
}
