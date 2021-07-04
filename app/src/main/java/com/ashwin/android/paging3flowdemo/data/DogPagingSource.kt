package com.ashwin.android.paging3flowdemo.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ashwin.android.paging3flowdemo.Constant
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class DogPagingSource @Inject constructor(private val dogApi: DogApi) : PagingSource<Int, Dog>() {
    override fun getRefreshKey(state: PagingState<Int, Dog>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dog> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val dogs = dogApi.getAllDogs(page, params.loadSize)
            Log.d(Constant.TAG, "UnsplashPagingSource: load: success: $page, ${dogs.size}")
            LoadResult.Page(
                data = dogs,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (dogs.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            Log.e(Constant.TAG, "UnsplashPagingSource: load: IOException")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e(Constant.TAG, "UnsplashPagingSource: load: HttpException")
            LoadResult.Error(e)
        }
    }
}
