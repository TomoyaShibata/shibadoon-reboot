package com.tomoyashibata.shibadoon.ui.createtoot

import androidx.lifecycle.ViewModel
import com.tomoyashibata.shibadoon.model.usecase.CreateTootUseCase
import com.tomoyashibata.shibadoon.ui.async
import com.tomoyashibata.shibadoon.ui.ui

class CreateTootViewModel(
  private val createTootUseCase: CreateTootUseCase
) : ViewModel() {
  fun onClickPostToot() {
    ui {
      async { this@CreateTootViewModel.createTootUseCase.execute("にゃーん #テスト") }.await()
    }
  }
}
