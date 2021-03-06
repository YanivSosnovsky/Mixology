package com.yanivsos.mixological.ui.fragments

import android.os.Bundle
import android.view.View
import com.yanivsos.mixological.BuildConfig
import com.yanivsos.mixological.R
import com.yanivsos.mixological.databinding.FragmentSettingsBinding
import com.yanivsos.mixological.ui.models.AppSettings
import com.yanivsos.mixological.ui.view_model.SettingsViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVersion()
        observeDarkMode()
    }

    private fun setVersion() {
        binding.settingsVersionTv.text = getString(R.string.settings_version, BuildConfig.VERSION_NAME)
    }

    private fun observeDarkMode() {
        AppSettings
            .darkModeEnabledFlow
            .withLifecycle()
            .onEach { darkModeEnabled -> binding.settingsDarkMode.isChecked = darkModeEnabled }
            .launchIn(viewLifecycleScope())

        binding
            .settingsDarkMode
            .checkedChangedChannel
            .withLifecycle()
            .onEach { darkModeEnabled -> onDarkModeEnabled(darkModeEnabled) }
            .launchIn(viewLifecycleScope())
    }

    private fun onDarkModeEnabled(darkModeEnabled: Boolean) {
        Timber.d("onDarkModeEnabled: $darkModeEnabled")
        settingsViewModel.toggleDarkMode(darkModeEnabled)
    }
}
