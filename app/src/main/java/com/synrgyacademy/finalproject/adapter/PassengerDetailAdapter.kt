package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synrgyacademy.domain.model.passenger.PassengerDataModel
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.ItemPassengerDetailBinding

class PassengerDetailAdapter(
    var onclick: ((PassengerDataModel) -> Unit)? = null
) : ListAdapter<PassengerDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class PassengerViewHolder(private val binding: ItemPassengerDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PassengerDataModel) {
            binding.apply {
                textName.text =
                    itemView.context.getString(R.string.text_detail_order_with_value, data.type)
                textNameBookerValue.text = data.name
                textBornBookerValue.text = data.bornDate
                textGenderBookerValue.text = data.bornDate
                if (data.gender == "L") textGenderBookerValue.text =
                    itemView.context.getString(R.string.text_men) else itemView.context.getString(R.string.text_woman)

                if (!data.type.contains("Dewasa")) {
                    icPhone.visibility = View.GONE
                    textPhoneBooker.visibility = View.GONE
                    textPhoneBookerValue.visibility = View.GONE
                } else {
                    textPhoneBookerValue.text = data.phoneNumber
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
        return PassengerViewHolder(
            ItemPassengerDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as PassengerViewHolder).bind(data)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PassengerDataModel>() {
            override fun areItemsTheSame(
                oldItem: PassengerDataModel,
                newItem: PassengerDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: PassengerDataModel,
                newItem: PassengerDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}