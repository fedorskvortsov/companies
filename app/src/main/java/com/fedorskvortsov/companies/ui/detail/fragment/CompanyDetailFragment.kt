package com.fedorskvortsov.companies.ui.detail.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fedorskvortsov.companies.data.remote.api.CompanyService
import com.fedorskvortsov.companies.databinding.FragmentCompanyDetailBinding
import com.fedorskvortsov.companies.ui.detail.state.CompanyDetailUiState
import com.fedorskvortsov.companies.ui.detail.viewmodel.CompanyDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class CompanyDetailFragment : Fragment() {
    private var _binding: FragmentCompanyDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompanyDetailViewModel by viewModels()

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
        _binding = FragmentCompanyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView()")
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeUi() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is CompanyDetailUiState.Loading -> {

                    }
                    is CompanyDetailUiState.Success -> {
                        Timber.d(uiState.company.name)
                        binding.companyName.text = uiState.company.name
                        binding.companyUrl.text = uiState.company.www
                        binding.companyPhone.text = uiState.company.phone
                        val imageUrl = "${CompanyService.BASE_IMG_PATH}${uiState.company.img}"
                        Glide.with(binding.companyImage.context)
                            .load(imageUrl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(binding.companyImage)
                        binding.companyDescription.text = uiState.company.description
                    }
                    is CompanyDetailUiState.Error -> {
                        Timber.e(uiState.exception)
                    }
                }
            }
        }
    }
}