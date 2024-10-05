package com.example.psychoremastered.core.screen_route

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object StringSanitizer : KSerializer<String> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("UrlEncodedString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String {
        val decodedString = decoder.decodeString()
        return if (decodedString.contains("%2F")) {
            decodedString
        } else {
            URLDecoder.decode(decodedString, StandardCharsets.UTF_8.toString())
        }
    }

    override fun serialize(encoder: Encoder, value: String) {
        val encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
        encoder.encodeString(encoded)
    }
}