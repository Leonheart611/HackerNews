package com.mikaocto.hackernews.ui.newsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikaocto.hackernews.R
import com.mikaocto.hackernews.data.StoryDetail
import com.mikaocto.hackernews.databinding.FragmentNewsDetailBinding
import com.mikaocto.hackernews.ui.adapter.CommentListAdapter
import com.mikaocto.hackernews.util.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {
    private val viewModel: NewsDetailViewModel by viewModels()
    private val commentAdapter = CommentListAdapter()
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    private val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        viewModel.getStoryDetail(args.storyId)
    }

    private fun setupView() {
        with(binding) {
            tbStoryDetail.setNavigationOnClickListener { findNavController().popBackStack() }
            with(rvCommentList) {
                layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = commentAdapter
            }
            ivSaveFavorite.setOnClickListener {
                viewModel.saveTitle(tvDetailTitle.text.toString())
                context?.makeToast("Title Saved")
            }
        }
        viewModel.storyLiveData.observe(viewLifecycleOwner) {
            updateView(it)
        }
        viewModel.commentLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NewsDetailViewModel.NewsViewState.OnErrorComment -> {
                    context?.makeToast(it.message)
                }
                is NewsDetailViewModel.NewsViewState.OnErrorStory -> {
                    context?.makeToast(it.message)
                }
                is NewsDetailViewModel.NewsViewState.OnSuccessGetComment -> {
                    commentAdapter.submitList(it.data)
                    binding.pbLoadingDetail.isGone = true
                }
            }
        }
    }

    private fun updateView(data: StoryDetail) {
        with(binding) {
            tvDetailDate.text = data.date
            tvDetailTitle.text = data.title
            tvWriteBy.text = getString(R.string.written_by_text, data.by)
        }
    }

}