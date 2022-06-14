package com.mikaocto.hackernews.ui.newsdetail

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikaocto.hackernews.data.CommentDetail
import com.mikaocto.hackernews.data.StoryDetail
import com.mikaocto.hackernews.repository.NewsRepository
import com.mikaocto.hackernews.util.io
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    val repository: NewsRepository,
    sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _commentLiveData = MutableLiveData<NewsViewState>()
    val commentLiveData: LiveData<NewsViewState> by lazy { _commentLiveData }

    private val _storyLiveData = MutableLiveData<StoryDetail>()
    val storyLiveData: LiveData<StoryDetail> by lazy { _storyLiveData }

    fun getStoryDetail(id: Int) {
        viewModelScope.launch {
            io {
                repository.getStoryDetail(id).catch { e ->
                    _commentLiveData.postValue(NewsViewState.OnErrorStory(e.message.orEmpty()))
                }.collect {
                    _storyLiveData.postValue(it)
                    repository.getCommentList(it.kids).catch { e ->
                        _commentLiveData.postValue(NewsViewState.OnErrorComment(e.message.orEmpty()))
                    }.collect { data ->
                        _commentLiveData.postValue(NewsViewState.OnSuccessGetComment(data))
                    }
                }
            }
        }
    }


    sealed class NewsViewState {
        class OnSuccessGetComment(val data: MutableList<CommentDetail>) : NewsViewState()
        class OnErrorComment(val message: String) : NewsViewState()
        class OnErrorStory(val message: String) : NewsViewState()
    }

}