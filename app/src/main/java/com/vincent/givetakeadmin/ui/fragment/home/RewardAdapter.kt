package com.vincent.givetakeadmin.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.data.source.response.reward.RewardItem
import com.vincent.givetakeadmin.databinding.ItemRewardLayoutBinding

class RewardAdapter: RecyclerView.Adapter<RewardAdapter.ViewHolder>() {

    private var oldItemList = emptyList<RewardItem>()

    class ViewHolder(val binding: ItemRewardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRewardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            Glide.with(itemView.context)
                .load(oldItemList[position].photo)
                .placeholder(R.drawable.ic_load)
                .into(binding.imgItemReward)
            binding.txtPrice.text = oldItemList[position].price.toString() + " Pts"
            binding.txtAvailable.text = if (oldItemList[position].stock <= 0) "Stok Habis" else "Stok Tersedia"
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, oldItemList[position].name, Toast.LENGTH_SHORT ).show()
            }
        }
    }

    override fun getItemCount(): Int = oldItemList.size

    fun setData(newList: List<RewardItem>) {
        val diffUtil = StoriesDiffCallback(oldItemList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldItemList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}

class StoriesDiffCallback(
    private val oldList: List<RewardItem>,
    private val newList: List<RewardItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].name != newList[newItemPosition].name -> {
                false
            }
            oldList[oldItemPosition].photo != newList[newItemPosition].photo -> {
                false
            }
            oldList[oldItemPosition].price != newList[newItemPosition].price -> {
                false
            }
            oldList[oldItemPosition].stock != newList[newItemPosition].stock -> {
                false
            }
            else -> true
        }
    }

}