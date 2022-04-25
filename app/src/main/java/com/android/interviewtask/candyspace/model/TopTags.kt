package com.android.interviewtask.candyspace.model


import com.google.gson.annotations.SerializedName

data class TopTags(
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("items")
    val tagItems: List<TagItem>,
    @SerializedName("quota_max")
    val quotaMax: Int,
    @SerializedName("quota_remaining")
    val quotaRemaining: Int
)