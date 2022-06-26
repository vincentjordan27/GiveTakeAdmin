package com.vincent.givetakeadmin.ui.fragment.reward

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vincent.givetakeadmin.data.source.response.advice.AdviceItem
import com.vincent.givetakeadmin.data.source.response.request.RewardRequest
import com.vincent.givetakeadmin.databinding.ItemAdviceLayoutBinding
import com.vincent.givetakeadmin.databinding.ItemRequestRewardLayoutBinding
import com.vincent.givetakeadmin.ui.fragment.advice.AdviceListAdapter
import com.vincent.givetakeadmin.ui.fragment.advice.AdvicesDiffCallback

class RewardRequestListAdapter : RecyclerView.Adapter<RewardRequestListAdapter.ViewHolder>() {

    private var oldItemList = emptyList<RewardRequest>()

    class ViewHolder(val binding: ItemRequestRewardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemRequestRewardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RewardRequestListAdapter.ViewHolder, position: Int) {
        with(holder) {
            binding.tvName.text = oldItemList[position].rewardName
            binding.tvDate.text = oldItemList[position].date
            binding.tvUser.text = oldItemList[position].name
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, oldItemList[position].name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = oldItemList.size

    fun setData(newList: List<RewardRequest>) {
        val diffUtil = RewardsRequestDiffCallback(oldItemList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldItemList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}

class RewardsRequestDiffCallback(
    private val oldList: List<RewardRequest>,
    private val newList: List<RewardRequest>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return false
    }

}