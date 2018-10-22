package com.tomoyashibata.shibadoon.ui.hometimeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.tomoyashibata.shibadoon.databinding.FragmentHomeTimelineBinding
import com.tomoyashibata.shibadoon.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home_timeline.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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
    this.viewModel.also {
      it.onChangedStatusesEvent.observe(this, Observer { this.updateHomeTimelineController() })
      it.requestScrollToLatestStatusEvent.observe(this, Observer { this.scrollToLatestStatus() })
    }
  }

  private lateinit var homeTimelineController: HomeTimelineController

  private fun setupRecyclerView() {
    this.homeTimelineController = HomeTimelineController(this.viewModel.statuses, false, this.viewModel)
    this.home_timeline_recycler.setControllerAndBuildModels(this.homeTimelineController)
    this.homeTimelineController.fetch.observe(this, Observer { this@HomeTimelineFragment.viewModel.fetchOldHomeTimeline() })
  }

  private fun updateHomeTimelineController() {
    this.homeTimelineController.requestModelBuild()
  }

  private fun scrollToLatestStatus() {
    this.home_timeline_recycler.smoothScrollToPosition(0)
  }
}
