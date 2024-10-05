package com.example.psychoremastered.domain.model

import android.os.Parcelable
import com.example.psychoremastered.core.screen_route.StringSanitizer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Serializable
@Parcelize
data class User(
    val userId: String,
    val email: String?,
    val displayName: String?,
    @Serializable(with = StringSanitizer::class)
    val profilePictureUri: String?
) : Parcelable