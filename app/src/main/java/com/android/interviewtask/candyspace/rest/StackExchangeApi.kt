package com.android.interviewtask.candyspace.rest

import com.android.interviewtask.candyspace.model.TopTags
import com.android.interviewtask.candyspace.model.UsersList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackExchangeApi {

    @GET(METHOD_USER)
    suspend fun getUsersList(
        @Query(QUERY_INNAME)inname:String="",
        @Query(QUERY_PAGESIZE)size:String="20",
        @Query(QUERY_ORDER)order:String="asc",
        @Query(QUERY_SORT)sort: String="name",
        @Query(QUERY_SITE)site: String="stackoverflow"
    ): Response<UsersList>

    @GET(METHOD_TOPTAGS)
    suspend fun getTopTags(
        @Path(PATH_ID)userid:String,
        @Query(QUERY_SITE)site: String="stackoverflow"
    ): Response<TopTags>

    companion object {
        const val BASE_URL = "https://api.stackexchange.com/2.3/"
        private const val METHOD_TOPTAGS = "users/{id}/top-tags"
        private const val METHOD_USER = "users"
        private const val PATH_ID = "id"
        private const val QUERY_INNAME = "inname"
        private const val QUERY_PAGESIZE = "pagesize"
        private const val QUERY_ORDER = "order"
        private const val QUERY_SORT = "sort"
        private const val QUERY_SITE = "site"
    }
}