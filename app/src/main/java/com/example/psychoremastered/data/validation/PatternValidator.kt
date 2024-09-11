package com.example.psychoremastered.data.validation

interface PatternValidator {
    fun matches(value: String): Boolean
}