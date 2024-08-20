package com.example.psychoremstered.therapist_registration.model

import android.content.res.Resources
import com.example.psychoremstered.R

data class RegistrationPage(
    val title: String,
    val description: String,
    val stepNumber: Int
)

val registrationPages = listOf(
    RegistrationPage(
        title = "Specialization",
        description = "Choose the most relevant specializations for you. You can choose several.",
        stepNumber = 1
    ),
    RegistrationPage(
        title = "Work fields",
        description = "Choose the most relevant work fields for you. You can choose several",
        stepNumber = 2
    ),
    RegistrationPage(
        title = "Language",
        description = "Choose languages you work in. You can choose several.",
        stepNumber = 3
    ),
    RegistrationPage(
        title = "Description",
        description = "Add information about yourself. Tell your future clients what they should know about you and your ways of treatment. There are must be more than 500 symbols and less than 1500.",
        stepNumber = 4
    ),
    RegistrationPage(
        title = "Price",
        description = "Add estimated price for one session with you. You should indicate the price in dollars.",
        stepNumber = 5
    ),
    RegistrationPage(
        title = "Degree",
        description = "On this page you can add 1 image of your document for each degree. Please, be sure that image has proper quality.",
        stepNumber = 6
    )
)