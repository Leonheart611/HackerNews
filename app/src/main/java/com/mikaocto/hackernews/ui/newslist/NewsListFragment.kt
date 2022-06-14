package com.mikaocto.hackernews.ui.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikaocto.hackernews.databinding.FragmentNewsListBinding
import com.mikaocto.hackernews.ui.adapter.NewsListAdapter
import com.mikaocto.hackernews.util.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment(), NewsListAdapter.OnNewsClicklistener {
    private val viewModel: NewsListViewModel by viewModels()
    private val newsAdapter = NewsListAdapter(this)

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        viewModel.getTopNews()
    }

    private fun setupView() {
        with(binding) {
            with(rvNewsList) {
                layoutManager =
                    GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
                adapter = newsAdapter
            }
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NewsListViewModel.NewsListViewState.OnError -> {
                    context?.makeToast(it.message)
                    binding.pbLoading.isGone = true
                }
                is NewsListViewModel.NewsListViewState.SuccessGetNewsData -> {
                    newsAdapter.submitList(it.data)
                    binding.pbLoading.isGone = true
                }
            }
        }

    }

    override fun onItemClick(id: Int) {
        val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(id)
        findNavController().navigate(action)
    }

}