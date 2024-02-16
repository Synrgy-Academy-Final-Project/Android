package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgyacademy.domain.model.tourism.TourismDataModel
import com.synrgyacademy.finalproject.databinding.ItemPopularPlaceBinding
import com.synrgyacademy.finalproject.utils.StringUtils.shortenString

class PopularPlacesAdapter(
    var onclick: ((TourismDataModel) -> Unit)? = null
) : ListAdapter<TourismDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class PopularPlacesViewHolder(private val binding: ItemPopularPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TourismDataModel) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.imageLink)
                    .into(ivPlace)

                tvPlaceName.text = data.tourismName
                tvPlaceLocation.text = data.tourismLocation.shortenString(15)
            }
        }

        init {
            binding.root.setOnClickListener {
                onclick?.invoke(getItem(bindingAdapterPosition))
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<TourismDataModel>() {
            override fun areItemsTheSame(
                oldItem: TourismDataModel,
                newItem: TourismDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: TourismDataModel,
                newItem: TourismDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}