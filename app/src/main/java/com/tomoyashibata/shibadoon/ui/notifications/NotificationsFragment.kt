package com.tomoyashibata.shibadoon.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.tomoyashibata.shibadoon.databinding.FragmentNotificationsBinding
import com.tomoyashibata.shibadoon.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : BaseFragment() {
  private val viewModel: NotificationsViewModel by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentNotificationsBinding.inflate(inflater, container, false)
    binding.viewModel = this.viewModel
    binding.setLifecycleOwner(this)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    this.subscribeToNavigationChanges()
    this.setupRecyclerView()
  }

  override fun subscribeToNavigationChanges() {
    this.viewModel.also {
      it.onChangeNotificationsEvent.observe(this, Observer { this.updateHomeTimelineController() })
    }
  }

  private lateinit var notificationsController: NotificationsController

  private fun setupRecyclerView() {
    this.notificationsController = NotificationsController(this.viewModel.notifications)
    this.notifications_recycler.setControllerAndBuildModels(this.notificationsController)
  }

  private fun updateHomeTimelineController() {
    this.notificationsController.requestModelBuild()
  }
}
