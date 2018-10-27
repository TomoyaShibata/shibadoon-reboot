package com.tomoyashibata.shibadoon.ui.createtoot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tomoyashibata.shibadoon.R
import com.tomoyashibata.shibadoon.databinding.FragmentCreateTootBinding
import com.tomoyashibata.shibadoon.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTootFragment : BaseFragment() {
  private val viewModel: CreateTootViewModel by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentCreateTootBinding.inflate(inflater, container, false)
    binding.viewModel = this.viewModel
    binding.setLifecycleOwner(this)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    this.subscribeToNavigationChanges()
  }

  override fun subscribeToNavigationChanges() {
    this.viewModel.content.observe(this, Observer {
      this.viewModel.calculateRemainTootContentCount()
    })
    this.viewModel.onSuccessPostTootEvent.observe(this, Observer {
      this.findNavController().popBackStack()
      Toast.makeText(this.requireContext(), this.getString(R.string.create_toot_success_message), Toast.LENGTH_LONG).show()
    })
    this.viewModel.onErrorPostTootEvent.observe(this, Observer {
      Toast.makeText(this.requireContext(), this.getString(R.string.create_toot_error_message), Toast.LENGTH_LONG).show()
    })
  }
}
