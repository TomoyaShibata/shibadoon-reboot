package com.tomoyashibata.shibadoon.ui.hometimeline

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomoyashibata.shibadoon.databinding.FragmentHomeTimelineBinding
import com.tomoyashibata.shibadoon.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home_timeline.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeTimelineFragment : BaseFragment() {
  private val viewModel: HomeTimelineViewModel by viewModel()

  companion object {
    fun newInstance(): HomeTimelineFragment = HomeTimelineFragment()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentHomeTimelineBinding.inflate(inflater, container, false)
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
    this.viewModel.onChangedStatusesEvent.observe(this, Observer { this.updateRV() })
  }

  private lateinit var homeTimelineController: HomeTimelineController

  private fun setupRecyclerView() {
    this.homeTimelineController = HomeTimelineController(this.viewModel.statuses)
    this.home_timeline_recycler.setControllerAndBuildModels(this.homeTimelineController)
  }

  private fun updateRV() {
    this.homeTimelineController.requestModelBuild()
    this.home_timeline_recycler.smoothScrollToPosition(0)
  }
}
