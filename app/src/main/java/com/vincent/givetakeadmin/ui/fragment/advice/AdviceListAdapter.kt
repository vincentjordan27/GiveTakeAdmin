package com.vincent.givetakeadmin.ui.fragment.advice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vincent.givetakeadmin.data.source.response.advice.AdviceItem
import com.vincent.givetakeadmin.databinding.ItemAdviceLayoutBinding

class AdviceListAdapter : RecyclerView.Adapter<AdviceListAdapter.ViewHolder>() {

    private var oldItemList = emptyList<AdviceItem>()

    class ViewHolder(val binding: ItemAdviceLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemAdviceLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AdviceListAdapter.ViewHolder, position: Int) {
        with(holder) {
            binding.tvName.text = oldItemList[position].name
            binding.tvDate.text = oldItemList[position].date
            binding.tvType.text = oldItemList[position].category
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, oldItemList[position].name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = oldItemList.size

    fun setData(newList: List<AdviceItem>) {
        val diffUtil = AdvicesDiffCallback(oldItemList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldItemList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}

class AdvicesDiffCallback(
    private val oldList: List<AdviceItem>,
    private val newList: List<AdviceItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return when {
           oldList[oldItemPosition].id != newList[newItemPosition].id -> false
           oldList[oldItemPosition].reply != newList[newItemPosition].reply -> false
           else -> true
       }
    }

}