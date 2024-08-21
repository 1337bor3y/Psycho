package com.example.psychoremstered.therapist_registration.model

data class RegistrationPage(
    val title: String,
    val description: String,
    val checkBoxes: MutableMap<String, Boolean>,
    val stepNumber: Int
)

val registrationPages = listOf(
    RegistrationPage(
        title = "Specialization",
        description = "Choose the most relevant specializations for you. You can choose several.",
        checkBoxes = mutableMapOf("qweqew1" to false, "qweqeqe2" to false, "qweqew3" to false,
            "qweqeqe4" to false),
        stepNumber = 1
    ),
    RegistrationPage(
        title = "Work fields",
        description = "Choose the most relevant work fields for you. You can choose several",
        checkBoxes = mutableMapOf("qweqew1" to false, "qweqeqe2" to false, "qweqew3" to false, "qweqeqe4" to false,
            "qweqew5" to false, "qweqeqe6" to false, "qweqew7" to false, "qweqeqe8" to false),
        stepNumber = 2
    ),
    RegistrationPage(
        title = "Language",
        description = "Choose languages you work in. You can choose several.",
        checkBoxes = mutableMapOf("qqqq1" to false, "qweqeqe2" to false, "qweqew3" to false,
            "qweqeqe4" to false, "qweqew5" to false, "qweqeqe6" to false, "qweqew7" to false,
            "qweqeqe8" to false, "qweqew9" to false, "qweqeqe10" to false),
        stepNumber = 3
    ),
    RegistrationPage(
        title = "Description",
        description = "Add information about yourself. Tell your future clients what they should know about you and your ways of treatment. There are must be more than 500 symbols and less than 1500.",
        checkBoxes = mutableMapOf(),
        stepNumber = 4
    ),
    RegistrationPage(
        title = "Price",
        description = "Add estimated price for one session with you. You should indicate the price in dollars.",
        checkBoxes = mutableMapOf(),
        stepNumber = 5
    ),
    RegistrationPage(
        title = "Degree",
        description = "On this page you can add 1 image of your document for each degree. Please, be sure that image has proper quality.",
        checkBoxes = mutableMapOf(),
        stepNumber = 6
    )
)