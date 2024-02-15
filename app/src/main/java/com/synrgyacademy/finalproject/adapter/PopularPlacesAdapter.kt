package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgyacademy.domain.model.airport.PopularPlacesDataModel
import com.synrgyacademy.finalproject.databinding.ItemPopularPlaceBinding
import com.synrgyacademy.finalproject.utils.StringUtils.shortenString

class PopularPlacesAdapter(
    var onclick: ((PopularPlacesDataModel) -> Unit)? = null
) : ListAdapter<PopularPlacesDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class PopularPlacesViewHolder(private val binding: ItemPopularPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PopularPlacesDataModel) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(ivPlace)

                tvPlaceName.text = data.name
                tvPlaceLocation.text = data.location.shortenString(15)
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
        return PopularPlacesViewHolder(
            ItemPopularPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as PopularPlacesViewHolder).bind(data)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PopularPlacesDataModel>() {
            override fun areItemsTheSame(
                oldItem: PopularPlacesDataModel,
                newItem: PopularPlacesDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: PopularPlacesDataModel,
                newItem: PopularPlacesDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}