package com.lumisdinos.gameoffifteen.ui.policy

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lumisdinos.gameoffifteen.databinding.FragmentPrivacyBinding
import com.lumisdinos.gameoffifteen.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyFragment : Fragment() {

    private var _binding: FragmentPrivacyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PolicyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        (activity as MainActivity).clockTv?.visibility = View.GONE
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linkTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}