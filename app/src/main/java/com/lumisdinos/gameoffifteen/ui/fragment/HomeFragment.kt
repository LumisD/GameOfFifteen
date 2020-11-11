package com.lumisdinos.gameoffifteen.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.common.util.getFragmentWidthFromPref
import com.lumisdinos.gameoffifteen.common.util.setFragmentWidthInPref
import com.lumisdinos.gameoffifteen.databinding.FragmentHomeBinding
import com.lumisdinos.gameoffifteen.presentation.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preference: SharedPreferences

    private lateinit var viewBinding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = viewBinding.root
        setHasOptionsMenu(true)
        getFragmentWidthAndInitalLoadCells(viewBinding.root)
        return view
    }


    private fun getFragmentWidthAndInitalLoadCells(view: View) {
        val fragWidth = getFragmentWidthFromPref(preference, requireContext())
        if (fragWidth == 0) {
            view.post {
                viewModel.initialLoadCells(
                    view.width,
                    resources.getDimensionPixelSize(R.dimen.game_grid_margin),
                    resources.getDimensionPixelSize(R.dimen.cell_margin)
                )
                setFragmentWidthInPref(view.width, preference, requireContext())
            }
        } else {
            viewModel.initialLoadCells(
                fragWidth,
                resources.getDimensionPixelSize(R.dimen.game_grid_margin),
                resources.getDimensionPixelSize(R.dimen.cell_margin)
            )
        }
    }
}