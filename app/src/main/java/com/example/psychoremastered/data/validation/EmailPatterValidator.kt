package com.example.psychoremastered.data.validation

import android.util.Patterns
import javax.inject.Inject

class EmailPatterValidator @Inject constructor(): PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}