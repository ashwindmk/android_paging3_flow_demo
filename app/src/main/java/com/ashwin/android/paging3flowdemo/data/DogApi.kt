package com.ashwin.android.paging3flowdemo.data

import retrofit2.http.GET
import retrofit2.http.Query

interface DogApi {
    companion object{
        const val BASE_URL = "https://api.thedogapi.com"
    }

    @GET("v1/images/search")
    suspend fun getAllDogs(
        @Query("page") page:Int,
        @Query("limit") limit:Int
    ) : List<Dog>
}