package com.suy.donelistapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suy.donelistapp.databinding.ItemActivityBinding
import com.suy.donelistapp.room.entity.ActivityEntity

class ActivityAdapter(
    private val list: List<ActivityEntity>, private val listener: ItemSelectedListener
) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    inner class ViewHolder(private val binding: ItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(activityEntity: ActivityEntity) {
            binding.tvTitle.text = activityEntity.title
            itemView.setOnClickListener { listener.onItemSelected(activityEntity.id ?: 0) }
        }
    }
}