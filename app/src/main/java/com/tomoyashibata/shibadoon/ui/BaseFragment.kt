package com.tomoyashibata.shibadoon.ui

import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {
  protected abstract fun subscribeToNavigationChanges()
}
