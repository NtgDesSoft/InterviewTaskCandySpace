package com.android.interviewtask.candyspace.model


import com.google.gson.annotations.SerializedName

data class TagItem(
    @SerializedName("answer_count")
    val answerCount: Int,
    @SerializedName("answer_score")
    val answerScore: Int,
    @SerializedName("question_count")
    val questionCount: Int,
    @SerializedName("question_score")
    val questionScore: Int,
    @SerializedName("tag_name")
    val tagName: String,
    @SerializedName("user_id")
    val userId: Int
)