package com.hbs.burnout.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hbs.burnout.databinding.ItemMissionBinding
import com.hbs.burnout.model.Stage
import com.hbs.burnout.model.StageProgress

class MissionAdapter(private val successCallback: (View) -> (Unit)) :
    ListAdapter<Stage, MissionAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Stage>() {
        override fun areItemsTheSame(oldItem: Stage, newItem: Stage): Boolean =
            oldItem.round == newItem.round

        override fun areContentsTheSame(oldItem: Stage, newItem: Stage): Boolean =
            oldItem == newItem
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stage: Stage) {
            binding.tvMissionTitle.text = stage.title
            binding.tvMissionContent.text = stage.content
            binding.ivMissionBadge.setImageResource(MissionHelper.getBadge(stage.round))
            when (stage.progress) {
                StageProgress.PLAYING -> {
                    binding.ivNotCompletedImage.visibility = View.GONE
                    binding.root.setOnClickListener(makeRootClickListener())
                }
                StageProgress.COMPLETED -> {
                    binding.ivNotCompletedImage.visibility = View.GONE
                    binding.root.setOnClickListener(makeRootClickListener())
                }
                StageProgress.NOT_COMPLETED -> {
                    binding.ivNotCompletedImage.visibility = View.VISIBLE
                    binding.root.setOnClickListener(null)
                }
                else -> {
                    binding.ivNotCompletedImage.visibility = View.VISIBLE
                    binding.root.setOnClickListener(null)
                }
            }
        }

        private fun makeRootClickListener() = View.OnClickListener { successCallback(it) }
    }
}