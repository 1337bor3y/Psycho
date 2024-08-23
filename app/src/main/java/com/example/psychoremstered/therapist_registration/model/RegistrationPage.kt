package com.example.psychoremstered.therapist_registration.model

data class CheckBoxesRegistrationPage(
    val title: String,
    val description: String,
    val checkBoxes: MutableMap<String, Boolean>,
    val stepNumber: Int
)

data class TextFieldRegistrationPage(
    val title: String,
    val description: String,
    var text: String,
    val stepNumber: Int
)

data class DegreeRegistrationPage(
    val title: String,
    val documentNumber: Int,
    val description: String,
    var university: String,
    var speciality: String,
    var admissionYear: String,
    var graduationYear: String,
    var documentImage: String,
    val stepNumber: Int
)

val registrationPages = arrayListOf(
    CheckBoxesRegistrationPage(
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
    CheckBoxesRegistrationPage(
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
    CheckBoxesRegistrationPage(
        title = "Language",
        description = "Choose languages you work in. You can choose several.",
        checkBoxes = mutableMapOf(
            "English" to false, "Ukrainian" to false, "Polish" to false,
            "German" to false, "Spanish" to false, "Portuguese" to false, "Russian" to false
        ),
        stepNumber = 2
    ),
    TextFieldRegistrationPage(
        title = "Description",
        description = "Add information about yourself. Tell your future clients what they should know about you and your ways of treatment. There are must be more than 500 symbols and less than 1500.",
        text = "",
        stepNumber = 3
    ),
    TextFieldRegistrationPage(
        title = "Price",
        description = "Add estimated price for one session with you. You should indicate the price in dollars.",
        text = "",
        stepNumber = 4
    ),
    DegreeRegistrationPage(
        title = "Degree",
        documentNumber = 1,
        description = "On this page you can add 1 image of your document for each degree. Please, be sure that image has proper quality.",
        university = "",
        speciality = "",
        admissionYear = "",
        graduationYear = "",
        documentImage = "",
        stepNumber = 5
    )
)