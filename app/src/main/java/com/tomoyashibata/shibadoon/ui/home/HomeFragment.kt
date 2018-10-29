package com.tomoyashibata.shibadoon.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.zsmb.materialdrawerkt.builders.AccountHeaderBuilderKt
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem
import com.tomoyashibata.shibadoon.R
import com.tomoyashibata.shibadoon.databinding.FragmentHomeBinding
import com.tomoyashibata.shibadoon.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
  private val viewModel: HomeViewModel by viewModel()
  private lateinit var accountHeader: AccountHeader
  private var drawer: Drawer? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentHomeBinding.inflate(inflater, container, false)
    binding.viewModel = this.viewModel
    binding.setLifecycleOwner(this)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    this.subscribeToNavigationChanges()
    this.viewModel.checkEnableSavedToken()
  }

  override fun subscribeToNavigationChanges() {
    this.viewModel.onRequestNavigateToLoginFragmentEvent.observe(this, Observer { this.navigateToLoginFragment() })
    this.viewModel.enabledSavedTokenEvent.observe(this, Observer {
      this.setupDrawer()
      this.setupViewPager()
    })
    this.viewModel.postGetAccountsEvent.observe(this, Observer { this.setupDrawerAccountHeader() })
    this.viewModel.postChangeAccountEvent.observe(this, Observer { this.navigateToMainFragment() })
    this.viewModel.onClickCreateTootEvent.observe(this, Observer { this.navigateToCreateTootFragment() })
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
        iconUrl = account.avatar
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
          .withOnDrawerItemClickListener { _, _, _ ->
            this@HomeFragment.navigateToLoginFragment()
            return@withOnDrawerItemClickListener false
          }
          .withName("アカウントを追加")
          .withIcon(R.drawable.ic_add)
      )
    }
  }

  private fun setupDrawer() {
    if (this.drawer != null) return

    this.accountHeader = AccountHeaderBuilder()
      .withActivity(this.requireActivity())
      .build()

    this.drawer = DrawerBuilder()
      .withActivity(this.requireActivity())
      .withAccountHeader(this.accountHeader)
      .withTranslucentStatusBar(true)
      .withActionBarDrawerToggle(true)
      .build()

    this.viewModel.getAccounts()
  }

  private fun setupViewPager() {
    this.home_view_pager.adapter = HomeFragmentPagerAdapter(this.childFragmentManager)
  }

  private fun navigateToLoginFragment() =
    this.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)

  private fun navigateToMainFragment() =
    this.findNavController().navigate(R.id.action_mainFragment_self)

  private fun navigateToCreateTootFragment() =
    this.findNavController().navigate(R.id.action_mainFragment_to_createTootFragment)
}
