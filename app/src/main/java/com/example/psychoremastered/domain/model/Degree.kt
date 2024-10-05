package com.example.psychoremastered.domain.model

import android.os.Parcelable
import com.example.psychoremastered.core.screen_route.StringSanitizer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Degree(
    val id: Int,
    val university: String,
    val speciality: String,
    val admissionYear: String,
    val graduationYear: String,
    @Serializable(with = StringSanitizer::class)
    val documentImage: String
) : Parcelable