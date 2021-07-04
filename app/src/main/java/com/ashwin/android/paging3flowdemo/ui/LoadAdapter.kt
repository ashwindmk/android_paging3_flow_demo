package com.ashwin.android.paging3flowdemo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashwin.android.paging3flowdemo.databinding.LayoutErrorBinding

class LoadAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadAdapter.LoadViewHolder>() {
    class LoadViewHolder(private val binding: LayoutErrorBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                errorTextView.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val binding = LayoutErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadViewHolder(binding, retry)
    }
}
