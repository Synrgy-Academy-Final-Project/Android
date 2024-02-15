package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synrgyacademy.domain.model.passenger.PassengerTotal
import com.synrgyacademy.finalproject.databinding.ItemPassengerBinding

class PassengerAdapter(
    var onclick: ((String) -> Unit)? = null
) : ListAdapter<String, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class PassengerViewHolder(private val binding: ItemPassengerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.apply {
                tvPassengerTitle.text = data
            }
        }

        init {
            binding.root.setOnClickListener {
                onclick?.invoke(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return PassengerViewHolder(
            ItemPassengerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun submitList(data: PassengerTotal) {
        val list = mutableListOf<String>()
        repeat(data.adult) { list.add("Dewasa ${it + 1}") }
        repeat(data.child) { list.add("Anak ${it + 1}") }
        repeat(data.infant) { list.add("Bayi ${it + 1}") }
        super.submitList(list)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as PassengerViewHolder).bind(data)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean =
                oldItem == newItem
        }
    }
}