package com.lumisdinos.gameoffifteen.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.lumisdinos.gameoffifteen.databinding.FragmentSettingsBinding
import com.lumisdinos.gameoffifteen.presentation.SettingsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class SettingsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewBinding: FragmentSettingsBinding
    private val viewModel by viewModels<SettingsViewModel> { viewModelFactory }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = viewBinding.root
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).clock.visibility = View.GONE
        return view
    }
}