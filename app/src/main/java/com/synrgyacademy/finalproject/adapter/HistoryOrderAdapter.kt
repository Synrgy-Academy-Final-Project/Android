package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synrgyacademy.domain.model.airport.HistoryTransactionDataModel
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.ItemHistoryOrderBinding

class HistoryOrderAdapter(
    var onclick: ((HistoryTransactionDataModel) -> Unit)? = null
) : ListAdapter<HistoryTransactionDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class PassengerViewHolder(private val binding: ItemHistoryOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HistoryTransactionDataModel) {
            binding.apply {
                tvOrderId.text = itemView.context.getString(R.string.text_order_code, data.oderCode)
                tvOrderPrice.text = itemView.context.getString(
                    R.string.text_total_price_with_space,
                    data.totalPrice
                )
                tvOrderDeparture.text = data.departureCityCode
                tvOrderArrival.text = data.arrivalCityCode
                if (data.transactionStatus == "Pembayaran Berhasil") {
                    tvOrderStatus.setBackgroundColor(itemView.context.getColor(R.color.secondary_success))
                    tvOrderStatus.text = data.transactionStatus
                } else {
                    tvOrderStatus.setBackgroundColor(itemView.context.getColor(R.color.secondary_warning))
                    tvOrderStatus.text = itemView.context.getString(R.string.text_payment_failed)
                }
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
            ItemHistoryOrderBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<HistoryTransactionDataModel>() {
            override fun areItemsTheSame(
                oldItem: HistoryTransactionDataModel,
                newItem: HistoryTransactionDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: HistoryTransactionDataModel,
                newItem: HistoryTransactionDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}