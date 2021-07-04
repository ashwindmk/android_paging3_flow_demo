package com.ashwin.android.paging3flowdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ashwin.android.paging3flowdemo.data.Dog
import com.ashwin.android.paging3flowdemo.data.DogApi
import com.ashwin.android.paging3flowdemo.data.DogPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dogApi: DogApi
) : ViewModel() {
    fun getAllDogs(): Flow<PagingData<Dog>> = Pager(
        config = PagingConfig(10, enablePlaceholders = false),
        pagingSourceFactory = {
            DogPagingSource(dogApi)
        }
    ).flow.cachedIn(viewModelScope)
}