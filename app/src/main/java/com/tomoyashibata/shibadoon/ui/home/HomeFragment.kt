package com.tomoyashibata.shibadoon.ui.home

import android.arch.lifecycle.Observer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.zsmb.materialdrawerkt.builders.AccountHeaderBuilderKt
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem
import com.tomoyashibata.shibadoon.R
import com.tomoyashibata.shibadoon.databinding.FragmentHomeBinding
import com.tomoyashibata.shibadoon.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
  private val viewModel: HomeViewModel by viewModel()
  private lateinit var accountHeader: AccountHeader

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentHomeBinding.inflate(inflater, container, false)
    binding.viewModel = this.viewModel
    binding.setLifecycleOwner(this)
    this.setHasOptionsMenu(true)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    this.subscribeToNavigationChanges()
    this.setupActionBar()
    this.setupDrawer()
  }

  override fun subscribeToNavigationChanges() {
    this.viewModel.apply {
      onRequestNavigateToLoginFragmentEvent.observe(this@HomeFragment, Observer { this@HomeFragment.navigateToLoginFragment() })
      postGetAccountsEvent.observe(this@HomeFragment, Observer { this@HomeFragment.setupDrawerAccountHeader() })
      postChangeAccountEvent.observe(this@HomeFragment, Observer { this@HomeFragment.navigateToMainFragment() })
    }
  }

  private fun setupActionBar() {
    val activity = this.requireActivity() as AppCompatActivity
    activity.setSupportActionBar(this.toolbar)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
  }

  private fun setupDrawerAccountHeader() {
    val currentSavedAccount = this.viewModel.currentSavedAccount.value ?: return
    val savedAccounts = this.viewModel.savedAccounts.value ?: return

    savedAccounts.map { (accessTokenId, account) ->
      val displayName = if (account.displayName.isBlank()) account.username else account.displayName
      val canonicalUsername = "${account.username}@${Uri.parse(account.url).host}"

      val profile = AccountHeaderBuilderKt(this.requireActivity()).profile(displayName, canonicalUsername) {
        identifier = accessTokenId
        nameShown = true
        iconUrl = account.avatarStatic
        onClick { _, _, drawerItem ->
          this@HomeFragment.viewModel.changeCurrentSaved(drawerItem.identifier)
          return@onClick false
        }
      }
      this.accountHeader.addProfiles(profile)
      if (account.url == currentSavedAccount.url) this.accountHeader.activeProfile = profile
    }

    this.accountHeader.apply {
      setHeaderBackground(ImageHolder(currentSavedAccount.headerStatic))
      addProfiles(
        ProfileSettingDrawerItem()
          .withOnDrawerItemClickListener { view, position, drawerItem ->
            this@HomeFragment.navigateToLoginFragment()
            return@withOnDrawerItemClickListener false
          }
          .withName("アカウントを追加")
          .withIcon(R.drawable.ic_add)
      )
    }
  }

  private fun setupDrawer() {
    this.accountHeader = AccountHeaderBuilder()
      .withActivity(this.requireActivity())
      .build()

    DrawerBuilder()
      .withActivity(this.requireActivity())
      .withAccountHeader(this.accountHeader)
      .withToolbar(this.toolbar)
      .withSliderBackgroundColorRes(R.color.colorPrimary)
      .build()
  }

  private fun navigateToLoginFragment() = this.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
  private fun navigateToMainFragment() = this.findNavController().navigate(R.id.action_mainFragment_self)
}
