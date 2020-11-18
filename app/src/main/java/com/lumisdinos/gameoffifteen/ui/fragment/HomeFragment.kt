package com.lumisdinos.gameoffifteen.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.common.Event
import com.lumisdinos.gameoffifteen.databinding.FragmentHomeBinding
import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import com.lumisdinos.gameoffifteen.presentation.HomeViewModel
import com.lumisdinos.gameoffifteen.ui.dialog.showMaterialAlertDialog
import com.lumisdinos.gameoffifteen.ui.view.DragUtil
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dragUtil: DragUtil

    private var viewBinding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        setHasOptionsMenu(true)
        initCellLoadWhenViewIsDrawn(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.let {
            it.diceImage.setOnClickListener {
                viewModel.reloadCells()
            }
        }
    }


    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.gameState.observe(viewLifecycleOwner, { render(it) })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }


    private fun render(gameState: GameStateModel) {
        replaceCellsInLLayout(gameState.cells)
        showAlertDialog(gameState.showAlertDialog)
    }


    private fun initCellLoadWhenViewIsDrawn(view: View?) {
        view?.let {
            it.post {
                viewModel.initialLoadCells(
                    it.width,
                    resources.getDimensionPixelSize(R.dimen.space_small),
                    resources.getDimensionPixelSize(R.dimen.space_extra_xxsmall)
                )
            }
        }
    }


    private fun replaceCellsInLLayout(event: Event<List<Int>>) {
        event.getContentIfNotHandled()?.let {
            dragUtil.insertCellsInLLayout(
                it, viewBinding?.squareRL, viewModel::swapCellWithEmpty,
                layoutInflater,
                resources.getDimensionPixelSize(R.dimen.space_small),
                resources.getDimensionPixelSize(R.dimen.space_extra_xxsmall)
            )
        }
    }


    private fun showAlertDialog(event: Event<String>) {
        event.getContentIfNotHandled()?.let { action ->
            if (action.isNotEmpty()) {
                context?.let { cont -> showMaterialAlertDialog(cont, action) }
            }
        }
    }

}