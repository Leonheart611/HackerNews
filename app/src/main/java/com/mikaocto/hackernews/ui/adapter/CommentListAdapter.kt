package com.mikaocto.hackernews.ui.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mikaocto.hackernews.data.CommentDetail
import com.mikaocto.hackernews.databinding.CommentViewItemBinding

class CommentListAdapter :
    ListAdapter<CommentDetail, CommentListAdapter.CommentListHolder>(CommentDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListHolder {
        return CommentListHolder(
            CommentViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentListHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    class CommentListHolder(private val binding: CommentViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommentDetail) {
            with(binding) {
                tvCommentBy.text = data.by
                tvDetailComment.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(data.text, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(data.text)
                }
            }
        }
    }

    class CommentDiffUtil : DiffUtil.ItemCallback<CommentDetail>() {
        override fun areItemsTheSame(oldItem: CommentDetail, newItem: CommentDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CommentDetail, newItem: CommentDetail): Boolean {
            return oldItem == newItem
        }

    }

}