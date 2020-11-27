package com.lumisdinos.gameoffifteen.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lumisdinos.gameoffifteen.common.util.convertLongToDateString
import com.lumisdinos.gameoffifteen.common.util.formatToDigitalClock
import com.lumisdinos.gameoffifteen.common.util.isClickedShort
import com.lumisdinos.gameoffifteen.databinding.ItemAchiveBinding
import com.lumisdinos.gameoffifteen.domain.model.GameModel

class AchiveListAdapter(private val itemClickListener: OnGameItemClickListener) :
    ListAdapter<GameModel, AchiveListAdapter.ViewHolder>(GameDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemAchiveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameModel, clickListener: OnGameItemClickListener) {
            binding.timeTv.text = formatToDigitalClock(item.time)
            binding.dateTv.text = convertLongToDateString(item.endTime)

            binding.deleteIv.setOnClickListener {
                if (isClickedShort()) return@setOnClickListener
                clickListener.onItemDeleteClicked(item.id)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAchiveBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


interface OnGameItemClickListener {

    fun onItemDeleteClicked(id: Int)
}


class GameDiffCallback : DiffUtil.ItemCallback<GameModel>() {
    override fun areItemsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
        return oldItem == newItem
    }
}