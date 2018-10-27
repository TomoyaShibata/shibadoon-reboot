package com.tomoyashibata.shibadoon.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.tomoyashibata.shibadoon.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.setContentView(R.layout.main_activity)

    this.setupToolbar()
  }

  override fun onSupportNavigateUp() =
    findNavController(this, R.id.my_nav_host_fragment).navigateUp()

  override fun onNewIntent(intent: Intent?) {
    this.intent.data = intent?.data
  }

  fun setupToolbar() {
    this.setSupportActionBar(this.main_toolbar)
    this.setupActionBarWithNavController(findNavController(this, R.id.my_nav_host_fragment))
  }
}
