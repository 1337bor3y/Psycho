package com.example.psychoremastered.domain.model

import android.os.Parcelable
import com.example.psychoremastered.core.screen_route.StringSanitizer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Therapist(
    val id: String,
    @Serializable(with = StringSanitizer::class)
    val avatarUri: String,
    val email: String,
    val displayName: String,
    val specializations: List<String>,
    val workFields: List<String>,
    val languages: List<String>,
    val description: String,
    val price: String,
    val hasDegree: Boolean,
    val degrees: List<Degree>
) : Parcelable