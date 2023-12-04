package com.lumisdinos.gameoffifteen.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.common.util.formatToDigitalClock
import com.lumisdinos.gameoffifteen.databinding.FragmentHomeBinding
import com.lumisdinos.gameoffifteen.domain.model.GameStateModel
import com.lumisdinos.gameoffifteen.ui.MainActivity
import com.lumisdinos.gameoffifteen.ui.dialog.showMaterialAlertDialog
import com.lumisdinos.gameoffifteen.ui.view.AnimateUtil
import com.lumisdinos.gameoffifteen.ui.view.DragUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var dragUtil: DragUtil

    @Inject
    lateinit var animateUtil: AnimateUtil

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)

        initCellLoadWhenViewIsDrawn(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.diceImageLeft.setOnClickListener { viewModel.reloadCells() }
        binding.diceImageRight.setOnClickListener { viewModel.reloadCells() }
        Handler(Looper.getMainLooper()).postDelayed({
            (activity as MainActivity).clockTv?.visibility = View.VISIBLE
        }, 300)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.gameState.observe(viewLifecycleOwner) { render(it) }
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
        _binding = null
    }


    private fun render(gameState: GameStateModel) {
        if (gameState.isCellsUpdated) replaceCellsInLLayout(gameState.cells)
        if (gameState.isDialogUpdated) showAlertDialog(gameState.showAlertDialog)
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
        viewModel.cellsAreRendered()
        dragUtil.insertCellsInLLayout(
            cells, binding.squareRL, viewModel::swapCellWithEmpty,
            layoutInflater,
            resources.getDimensionPixelSize(R.dimen.space_small),
            resources.getDimensionPixelSize(R.dimen.space_extra_xxsmall),
            animateUtil
        )
        binding.let {
            animateUtil.animateDice(it.diceImageLeft, true)
            animateUtil.animateDice(it.diceImageRight, false)
        }
    }


    private fun updateTime(time: Long) {
        (activity as MainActivity).clockTv?.text = formatToDigitalClock(time)
    }


    private fun showAlertDialog(action: String) {
        if (action.isNotEmpty()) {
            context?.let { cont -> showMaterialAlertDialog(cont, action) }
            viewModel.dialogIsRendered()
        }
    }

}