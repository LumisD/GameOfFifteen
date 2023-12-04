package com.lumisdinos.gameoffifteen.ui.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.common.Resource
import com.lumisdinos.gameoffifteen.common.ResourceState
import com.lumisdinos.gameoffifteen.common.util.isClickedShort
import com.lumisdinos.gameoffifteen.databinding.FragmentListBinding
import com.lumisdinos.gameoffifteen.domain.model.GameModel
import com.lumisdinos.gameoffifteen.ui.MainActivity
import com.lumisdinos.gameoffifteen.ui.adapter.AchiveListAdapter
import com.lumisdinos.gameoffifteen.ui.adapter.OnGameItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), OnGameItemClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()
    private var listAdapter: AchiveListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        (activity as MainActivity).clockTv?.visibility = View.GONE
        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        viewModel.games.observe(viewLifecycleOwner, { updateGames(it) })
        viewModel.loadSolvedGames(false)
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
    }


    @Deprecated("Deprecated in Java")
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
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.noResultsTv.visibility = View.GONE
                }
                ResourceState.SUCCESS -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.noResultsTv.visibility = View.GONE
                }
                ResourceState.EMPTY -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.noResultsTv.visibility = View.VISIBLE
                }
                else -> {}
            }
            it.data?.let {
                listAdapter?.submitList(it)
            }
        }
    }


    private fun setupListAdapter() {
        listAdapter = AchiveListAdapter(this)
        binding.gameList.adapter = listAdapter
        binding.gameList.addItemDecoration(
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