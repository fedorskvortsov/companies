package com.fedorskvortsov.companies.ui.list.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ThemeUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.fedorskvortsov.companies.R
import com.fedorskvortsov.companies.databinding.FragmentCompanyListBinding
import com.fedorskvortsov.companies.ui.list.adapter.SimpleCompanyAdapter
import com.fedorskvortsov.companies.ui.list.state.CompanyListUiState
import com.fedorskvortsov.companies.ui.list.viewmodel.CompanyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class CompanyListFragment : Fragment() {
    private var _binding: FragmentCompanyListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompanyListViewModel by viewModels()

    override fun onAttach(context: Context) {
        Timber.d("onAttach()")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("onCreateView()")
        _binding = FragmentCompanyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        val adapter = SimpleCompanyAdapter()
        binding.companyList.adapter = adapter
        subscribeUi(adapter)
        initSwipeRefreshLayout()
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView()")
        super.onDestroyView()
        _binding = null
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.getCompanies()
            }
            setColorSchemeColors(R.attr.colorPrimary)
        }
    }

    private fun subscribeUi(adapter: SimpleCompanyAdapter) {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is CompanyListUiState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                    }
                    is CompanyListUiState.Success -> {
                        if (uiState.companies.isNotEmpty()) {
                            adapter.submitList(uiState.companies)
                        }
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is CompanyListUiState.Error -> {
                        Timber.e(uiState.exception)
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
        }
    }
}