package com.fedorskvortsov.companies.ui.list.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.fedorskvortsov.companies.data.remote.api.CompanyService
import com.fedorskvortsov.companies.databinding.ListItemCompanyBinding
import com.fedorskvortsov.companies.domain.entity.Company
import com.fedorskvortsov.companies.domain.entity.SimpleCompany
import com.fedorskvortsov.companies.ui.list.fragment.CompanyListFragmentDirections

class SimpleCompanyAdapter :
    ListAdapter<SimpleCompany, SimpleCompanyAdapter.ViewHolder>(CompanyDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemCompanyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val simpleCompany = getItem(position)
        holder.bind(simpleCompany)
    }

    class ViewHolder(
        private val binding: ListItemCompanyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SimpleCompany) {
            binding.apply {
                cardView.setOnClickListener { view ->
                    navigateToCompanyDetail(item, view)
                }
                companyItemTitle.text = item.name
                bindImageFromUrl(
                    binding.companyItemImage,
                    "${CompanyService.BASE_IMG_PATH}${item.img}"
                )
            }
        }

        private fun navigateToCompanyDetail(item: SimpleCompany, view: View) {
             val action = CompanyListFragmentDirections
                     .actionCompanyListFragmentToCompanyDetailFragment(item.id, item.name)
             view.findNavController().navigate(action)
        }

        private fun bindImageFromUrl(imageView: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(imageView.context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
            }
        }
    }
}

object CompanyDiffCallback : DiffUtil.ItemCallback<SimpleCompany>() {
    override fun areContentsTheSame(oldItem: SimpleCompany, newItem: SimpleCompany): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: SimpleCompany, newItem: SimpleCompany): Boolean {
        return oldItem.id == newItem.id
    }
}