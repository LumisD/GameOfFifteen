package com.lumisdinos.gameoffifteen.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.common.util.formatToDigitalClock
import com.lumisdinos.gameoffifteen.databinding.FragmentHomeBinding
import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import com.lumisdinos.gameoffifteen.presentation.HomeViewModel
import com.lumisdinos.gameoffifteen.ui.dialog.showMaterialAlertDialog
import com.lumisdinos.gameoffifteen.ui.view.AnimateUtil
import com.lumisdinos.gameoffifteen.ui.view.DragUtil
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dragUtil: DragUtil

    @Inject
    lateinit var animateUtil: AnimateUtil

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
        (activity as AppCompatActivity).clock.visibility = View.VISIBLE
        initCellLoadWhenViewIsDrawn(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.diceImageLeft?.setOnClickListener { viewModel.reloadCells() }
        viewBinding?.diceImageRight?.setOnClickListener { viewModel.reloadCells() }
    }


    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.gameState.observe(viewLifecycleOwner, { render(it) })
    }


    override fun onResume() {
        super.onResume()
        viewModel.getGame()
    }


    override fun onStop() {
        super.onStop()
        viewModel.saveGame()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }


    private fun render(gameState: GameStateModel) {
        if (gameState.isCellsUpdated) {
            replaceCellsInLLayout(gameState.cells)
            viewModel.cellsAreRendered()
        }
        if (gameState.isDialogUpdated) {
            showAlertDialog(gameState.showAlertDialog)
            viewModel.dialogIsRendered()
        }
        updateTime(gameState.time)
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


    private fun replaceCellsInLLayout(cells: List<Int>) {
        dragUtil.insertCellsInLLayout(
            cells, viewBinding?.squareRL, viewModel::swapCellWithEmpty,
            layoutInflater,
            resources.getDimensionPixelSize(R.dimen.space_small),
            resources.getDimensionPixelSize(R.dimen.space_extra_xxsmall),
            animateUtil
        )
        viewBinding?.let {
            animateUtil.animateDice(it.diceImageLeft, true)
            animateUtil.animateDice(it.diceImageRight, false)
        }
    }


    private fun updateTime(time: Long) {
        (activity as AppCompatActivity).clock.text = formatToDigitalClock(time)
    }


    private fun showAlertDialog(action: String) {
        if (action.isNotEmpty()) {
            context?.let { cont -> showMaterialAlertDialog(cont, action) }
        }
    }

}