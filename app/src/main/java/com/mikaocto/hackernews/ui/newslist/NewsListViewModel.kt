package com.mikaocto.hackernews.ui.newslist

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikaocto.hackernews.data.StoryDetail
import com.mikaocto.hackernews.repository.NewsRepository
import com.mikaocto.hackernews.util.io
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    val repository: NewsRepository,
    val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _newsLiveData = MutableLiveData<NewsListViewState>()
    val newsLiveData: LiveData<NewsListViewState> by lazy { _newsLiveData }


    fun getTopNews() {
        viewModelScope.launch {
            io {
                repository.getTopStoryList().catch { data ->
                    _newsLiveData.postValue(NewsListViewState.OnError(data.message.orEmpty()))
                }.collect {
                    _newsLiveData.postValue(NewsListViewState.SuccessGetNewsData(it))
                }
            }
        }
    }


    sealed class NewsListViewState {
        class SuccessGetNewsData(val data: MutableList<StoryDetail>) : NewsListViewState()
        class OnError(val message: String) : NewsListViewState()
    }

}