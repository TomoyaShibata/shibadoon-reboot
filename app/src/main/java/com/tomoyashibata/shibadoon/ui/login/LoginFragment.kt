package com.tomoyashibata.shibadoon.ui.login

import android.arch.lifecycle.Observer
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomoyashibata.shibadoon.databinding.FragmentLoginBinding
import com.tomoyashibata.shibadoon.model.data.Authentication
import com.tomoyashibata.shibadoon.ui.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
  private val viewModel: LoginViewModel by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentLoginBinding.inflate(inflater, container, false)
    binding.viewModel = this.viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    this.subscribeToNavigationChanges()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
  }

  override fun subscribeToNavigationChanges() {
    this.viewModel.onRegisterAppEvent.observe(this, Observer { it?.let { this.hoge(it) } })
  }

  fun hoge(authentication: Authentication) {
    val url = "https://m6n.onsen.tech/oauth/authorize?client_id=${authentication.clientId}&redirect_uri=${authentication.redirectUri}&response_type=code&scope=read+write+follow"
    val customTabs = CustomTabsIntent.Builder().build()
    customTabs.intent.data = Uri.parse(url)
    this.startActivityForResult(customTabs.intent, 100)
  }

  override fun onResume() {
    super.onResume()
    val code = this.requireActivity().intent?.data?.getQueryParameter("code")
    code?.let { this@LoginFragment.viewModel.onSuccessGetCode(it) }
  }
}
