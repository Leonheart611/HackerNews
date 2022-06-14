package com.mikaocto.hackernews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mikaocto.hackernews.R
import com.mikaocto.hackernews.data.StoryDetail
import com.mikaocto.hackernews.databinding.NewsViewItemBinding

class NewsListAdapter(val clicklistener: OnNewsClicklistener) :
    ListAdapter<StoryDetail, NewsListAdapter.NewsListHolder>(StoryDetailDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListHolder {
        return NewsListHolder(
            NewsViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsListHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    inner class NewsListHolder(private val binding: NewsViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoryDetail) {
            with(binding) {
                tvNewsTitle.text = data.title
                tvNewsComment.text = data.descendants.toString()
                tvNewsScore.text = this.root.context.getString(R.string.score_text, data.score)
                root.setOnClickListener {
                    clicklistener.onItemClick(data.id)
                }
            }
        }

    }

    class StoryDetailDiff : DiffUtil.ItemCallback<StoryDetail>() {
        override fun areItemsTheSame(oldItem: StoryDetail, newItem: StoryDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StoryDetail, newItem: StoryDetail): Boolean {
            return oldItem == newItem
        }

    }

    interface OnNewsClicklistener {
        fun onItemClick(id: Int)
    }
}