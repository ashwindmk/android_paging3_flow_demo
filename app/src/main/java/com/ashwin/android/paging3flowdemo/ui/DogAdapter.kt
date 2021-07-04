package com.ashwin.android.paging3flowdemo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ashwin.android.paging3flowdemo.data.Dog
import com.ashwin.android.paging3flowdemo.databinding.ItemDogBinding
import javax.inject.Inject

class DogAdapter @Inject constructor() : PagingDataAdapter<Dog, DogAdapter.DogViewHolder>(DOG_COMPARATOR) {
    companion object {
        val DOG_COMPARATOR = object : DiffUtil.ItemCallback<Dog>() {
            override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
                return oldItem == newItem
            }
        }
    }

    class DogViewHolder(private val binding: ItemDogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog) {
            binding.apply {
                image.load(dog.url)
                name.text = dog.id
            }
        }
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        dog?.let {
            holder.bind(dog)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }
}
