package com.tomoyashibata.shibadoon.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
  protected abstract fun subscribeToNavigationChanges()
}
