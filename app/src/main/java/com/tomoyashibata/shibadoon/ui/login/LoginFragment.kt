package com.tomoyashibata.shibadoon.ui.login

import android.arch.lifecycle.Observer
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.toast
import androidx.navigation.fragment.findNavController
import com.tomoyashibata.shibadoon.R
import com.tomoyashibata.shibadoon.databinding.FragmentLoginBinding
import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.ui.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginFragment : BaseFragment() {
  private val viewModel: LoginViewModel by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentLoginBinding.inflate(inflater, container, false)
    binding.viewModel = this.viewModel
    binding.setLifecycleOwner(this)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    this.subscribeToNavigationChanges()
  }

  override fun subscribeToNavigationChanges() {
    this.viewModel.instanceBlankErrorEvent.observe(this, Observer { this.showToast("正しいインスタンス名を入力してください") })
    this.viewModel.onRegisterAppEvent.observe(this, Observer { it?.let { this.hoge(it) } })
    this.viewModel.onLoginFinishEvent.observe(this, Observer { this.navigateToMainFragment() })
  }

  fun hoge(authentication: Authentication) {
    Timber.i(authentication.toString())
    val url = "https://${this.viewModel.instance.value}/oauth/authorize?client_id=${authentication.clientId}&redirect_uri=${authentication.redirectUri}&response_type=code&scope=read+write+follow"
    val customTabs = CustomTabsIntent.Builder().build()
    customTabs.intent.data = Uri.parse(url)
    this.startActivityForResult(customTabs.intent, 100)
  }

  private fun navigateToMainFragment() {
    this.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
  }

  private fun showToast(message: String) {
    this.requireContext().toast(message, Toast.LENGTH_LONG)
  }

  override fun onResume() {
    super.onResume()
    val code = this.requireActivity().intent?.data?.getQueryParameter("code")
    code?.let { this@LoginFragment.viewModel.onSuccessGetCode(it) }
  }
}
