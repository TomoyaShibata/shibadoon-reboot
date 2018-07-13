package com.tomoyashibata.shibadoon.ui.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.tomoyashibata.shibadoon.R
import com.tomoyashibata.shibadoon.databinding.FragmentHomeBinding
import com.tomoyashibata.shibadoon.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
  private val viewModel: HomeViewModel by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentHomeBinding.inflate(inflater, container, false)
    binding.viewModel = this.viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    this.subscribeToNavigationChanges()
    this.setupActionBar()
  }

  override fun subscribeToNavigationChanges() {
    this.viewModel.onRequestLoginEvent.observe(this, Observer { Navigation.findNavController(this.view!!).navigate(R.id.loginFragment) })
  }

  private fun setupActionBar() {
    val activity = activity as AppCompatActivity?
    activity?.setSupportActionBar(this.toolbar)
  }
}
