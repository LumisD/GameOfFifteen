package com.lumisdinos.gameoffifteen.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.common.Event
import com.lumisdinos.gameoffifteen.databinding.FragmentHomeBinding
import com.lumisdinos.gameoffifteen.domain.model.GameState
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository.Companion.ACTION_CONGRATULATIONS
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository.Companion.ACTION_UNSOLVABLE
import com.lumisdinos.gameoffifteen.presentation.HomeViewModel
import com.lumisdinos.gameoffifteen.ui.dialog.DialogListener
import com.lumisdinos.gameoffifteen.ui.dialog.getAlertDialog
import com.lumisdinos.gameoffifteen.ui.view.DragUtil
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : DaggerFragment(), DialogListener {

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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.gameState.observe(viewLifecycleOwner, { render(it) })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }


    private fun render(gameState: GameState) {
        replaceCellsInLLayout(gameState.cells)
        showAlertDialog(gameState.showAlertDialog)
    }


    private fun initCellLoadWhenViewIsDrawn(view: View?) {
        view?.let {
            it.post {
                viewModel.initialLoadCells(
                    it.width,
                    resources.getDimensionPixelSize(R.dimen.game_grid_margin),
                    resources.getDimensionPixelSize(R.dimen.cell_margin)
                )
            }
        }
    }


    private fun replaceCellsInLLayout(event: Event<List<Int>>) {
        event.getContentIfNotHandled()?.let {
            dragUtil.insertCellsInLLayout(
                it, viewBinding?.squareRL, viewModel::swapCellWithEmpty,
                layoutInflater,
                resources.getDimensionPixelSize(R.dimen.game_grid_margin),
                resources.getDimensionPixelSize(R.dimen.cell_margin)
            )
        }
    }


    private fun showAlertDialog(event: Event<String>) {
        event.getContentIfNotHandled()?.let {
            val title: String
            val message: String
            val ok = getString(R.string.ok)

            when (it) {
                ACTION_CONGRATULATIONS -> {
                    title = getString(R.string.winner)
                    message = getString(R.string.congratulations_you_solved_it)
                }
                ACTION_UNSOLVABLE -> {
                    title = getString(R.string.finish)
                    message = getString(R.string.sorry_unsolvable)
                }
                else -> {
                    return
                }
            }

            getAlertDialog(
                requireContext(),
                it,//action
                this,
                title,
                message,
                ok
            ).show()
        }
    }


    //  -- DialogListener --

    override fun onPositiveDialogClick(result: List<String>) {
        when (result[0]) {
            ACTION_CONGRATULATIONS -> {
            }
            ACTION_UNSOLVABLE -> {
            }
        }
    }

    override fun onNegativeDialogClick(result: List<String>) {
    }

    override fun onNeutralDialogClick(result: List<String>) {
    }

}