package com.synrgyacademy.finalproject.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synrgyacademy.domain.model.airport.PromotionsDataModel
import com.synrgyacademy.finalproject.databinding.ItemPromoBinding

class PromoAdapter(
    var onclick: ((PromotionsDataModel) -> Unit)? = null
) : ListAdapter<PromotionsDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class TicketViewHolder(private val binding: ItemPromoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PromotionsDataModel) {
            binding.apply {
                tvPromoTitle.text = data.description
                tvPromoDesc.text = data.terms
                tvCopy.text = data.code

                ivCopy.setOnClickListener {
                    val clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Promo Code", data.code)
                    clipboard.setPrimaryClip(clip)

                    Toast.makeText(it.context, "Kode promo berhasil disalin", Toast.LENGTH_SHORT).show()
                }
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
        return TicketViewHolder(
            ItemPromoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as TicketViewHolder).bind(data)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PromotionsDataModel>() {
            override fun areItemsTheSame(
                oldItem: PromotionsDataModel,
                newItem: PromotionsDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: PromotionsDataModel,
                newItem: PromotionsDataModel
            ): Boolean =
                oldItem == newItem
        }
    }
}