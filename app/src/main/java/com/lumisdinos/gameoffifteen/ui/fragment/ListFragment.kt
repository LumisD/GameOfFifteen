package com.lumisdinos.gameoffifteen.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.common.Resource
import com.lumisdinos.gameoffifteen.common.ResourceState
import com.lumisdinos.gameoffifteen.common.util.isClickedShort
import com.lumisdinos.gameoffifteen.databinding.FragmentListBinding
import com.lumisdinos.gameoffifteen.domain.model.GameModel
import com.lumisdinos.gameoffifteen.presentation.ListViewModel
import com.lumisdinos.gameoffifteen.ui.adapter.AchiveListAdapter
import com.lumisdinos.gameoffifteen.ui.adapter.OnGameItemClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import timber.log.Timber
import javax.inject.Inject

class ListFragment : DaggerFragment(), OnGameItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewBinding: FragmentListBinding
    private val viewModel by viewModels<ListViewModel> { viewModelFactory }
    private var listAdapter: AchiveListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentListBinding.inflate(inflater, container, false)
        val view = viewBinding.root
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).clock.visibility = View.GONE
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        viewModel.games.observe(viewLifecycleOwner, { updateGames(it) })
        viewModel.loadSolvedGames(false)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_switch -> {
                if (isClickedShort()) return true
                if (viewModel.isOrderedByShortestTime) {
                    item.icon = AppCompatResources.getDrawable(requireContext(),R.drawable.ic_baseline_sort_white_24)
                } else {
                    item.icon = AppCompatResources.getDrawable(requireContext(),R.drawable.ic_outline_list_white_24)
                }
                viewModel.loadSolvedGames(!viewModel.isOrderedByShortestTime)
                return true
            }

        }
        return false
    }


    private fun updateGames(resource: Resource<List<GameModel>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                    viewBinding.pbLoading.visibility = View.VISIBLE
                    viewBinding.noResultsTv.visibility = View.GONE
                }
                ResourceState.SUCCESS -> {
                    viewBinding.pbLoading.visibility = View.GONE
                    viewBinding.noResultsTv.visibility = View.GONE
                }
                ResourceState.EMPTY -> {
                    viewBinding.pbLoading.visibility = View.GONE
                    viewBinding.noResultsTv.visibility = View.VISIBLE
                }
            }
            it.data?.let {
                listAdapter?.submitList(it)
            }
        }
    }


    private fun setupListAdapter() {
        listAdapter = AchiveListAdapter(this)
        viewBinding.gameList.adapter = listAdapter
        viewBinding.gameList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
    }


    // -- OnGameItemClickListener --

    override fun onItemDeleteClicked(id: Int) {
        viewModel.deleteGame(id)

    }
}