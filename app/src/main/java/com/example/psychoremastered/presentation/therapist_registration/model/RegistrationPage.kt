package com.example.psychoremastered.presentation.therapist_registration.model

data class RegistrationPage(
    val title: String,
    val description: String,
    val checkBoxes: MutableMap<String, Boolean>,
    val stepNumber: Int
)

val registrationPages = arrayListOf(
    RegistrationPage(
        title = "Specialization",
        description = "Choose the most relevant specializations for you. You can choose several.",
        checkBoxes = mutableMapOf(
            "Psychologist" to false, "Psychotherapist" to false,
            "Psychiatrist" to false, "Sexologist" to false, "Psychoanalyst" to false,
            "Gestalt therapist" to false, "Nutritionist" to false, "Art therapy" to false,
            "Behavioral analysis" to false
        ),
        stepNumber = 0
    ),
    RegistrationPage(
        title = "Work fields",
        description = "Choose the most relevant work fields for you. You can choose several",
        checkBoxes = mutableMapOf(
            "Body therapy" to false,
            "Child-parent relations" to false,
            "Coaching" to false,
            "Cognitive behavioral therapy" to false,
            "Crisis counseling" to false,
            "Dialectical behavior therapy" to false,
            "Sexology" to false,
            "Dialysis" to false,
            "Eating disorders" to false,
            "Emotional psychosomatic disorders" to false,
            "Experience violence" to false,
            "Gestalt approach" to false,
            "Integrative approach" to false,
            "Mindfulness" to false,
            "Positive psychotherapy" to false,
            "Post-traumatic stress disorder" to false,
            "Work with preverbal injuries" to false,
            "Psychoanalysis" to false,
            "Psychosomatics" to false,
            "Rational-emotive therapy" to false,
            "Sand therapy" to false
        ),
        stepNumber = 1
    ),
    RegistrationPage(
        title = "Language",
        description = "Choose languages you work in. You can choose several.",
        checkBoxes = mutableMapOf(
            "English" to false, "Ukrainian" to false, "Polish" to false,
            "German" to false, "Spanish" to false, "Portuguese" to false, "Russian" to false
        ),
        stepNumber = 2
    ),
    RegistrationPage(
        title = "Description",
        description = "Add information about yourself. Tell your future clients what they should know about you and your ways of treatment. There are must be more than 500 symbols and less than 1500.",
        checkBoxes = mutableMapOf(),
        stepNumber = 3
    ),
    RegistrationPage(
        title = "Price",
        description = "Add estimated price for one session with you. You should indicate the price in dollars.",
        checkBoxes = mutableMapOf(),
        stepNumber = 4
    ),
    RegistrationPage(
        title = "Degree",
        description = "On this page you can add 1 image of your document for each degree. Please, be sure that image has proper quality.",
        checkBoxes = mutableMapOf(),
        stepNumber = 5
    )
)