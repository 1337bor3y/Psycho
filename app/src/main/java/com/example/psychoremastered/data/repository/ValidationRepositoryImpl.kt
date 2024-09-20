package com.example.psychoremastered.data.repository

import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.data.validation.EmailPatterValidator
import com.example.psychoremastered.data.validation.PatternValidator
import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.repository.ValidationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidationRepositoryImpl @Inject constructor(
    private val patterValidator: PatternValidator
) : ValidationRepository {

    override fun validateProfileImage(profileImage: String): Flow<Resource<Boolean>> = flow {
        if (profileImage.isBlank()) {
            emit(Resource.Success<Boolean>(false))
            emit(Resource.Error<Boolean>("Profile image can't be blank"))
        } else {
            emit(Resource.Success<Boolean>(true))
        }
    }

    override fun validateFirstName(firstName: String): Flow<Resource<Boolean>> = flow {
        if (firstName.isBlank()) {
            emit(Resource.Success<Boolean>(false))
            emit(Resource.Error<Boolean>("First name can't be blank"))
        } else {
            emit(Resource.Success<Boolean>(true))
        }
    }

    override fun validateSurname(surname: String): Flow<Resource<Boolean>> = flow {
        if (surname.isBlank()) {
            emit(Resource.Success<Boolean>(false))
            emit(Resource.Error<Boolean>("Surname can't be blank"))
        } else {
            emit(Resource.Success<Boolean>(true))
        }
    }

    override fun validateEmail(email: String): Flow<Resource<Boolean>> = flow {
        if (email.isBlank()) {
            emit(Resource.Success<Boolean>(false))
            emit(Resource.Error<Boolean>("Email can't be blank"))
        } else if (!patterValidator.matches(email)) {
            emit(Resource.Success<Boolean>(false))
            emit(Resource.Error<Boolean>("Invalid email pattern"))
        } else {
            emit(Resource.Success<Boolean>(true))
        }
    }

    override fun validatePassword(password: String): Flow<Resource<Boolean>> = flow {
        if (password.isBlank()) {
            emit(Resource.Success<Boolean>(false))
            emit(Resource.Error<Boolean>("Password can't be blank"))
        } else if (password.length <= Constants.PASSWORD_LENGTH) {
            emit(Resource.Success<Boolean>(false))
            emit(
                Resource.Error<Boolean>(
                    "Password length must be more than "
                            + Constants.PASSWORD_LENGTH
                )
            )
        } else {
            emit(Resource.Success<Boolean>(true))
        }
    }

    override fun validateConfirmPassword(
        password: String,
        confirmPassword: String
    ): Flow<Resource<Boolean>> = flow {
        if (confirmPassword.isBlank()) {
            emit(Resource.Success<Boolean>(false))
            emit(Resource.Error<Boolean>("Confirm password can't be blank"))
        } else if (confirmPassword.length <= Constants.PASSWORD_LENGTH) {
            emit(Resource.Success<Boolean>(false))
            emit(
                Resource.Error<Boolean>(
                    "Confirm password length must be more than "
                            + Constants.PASSWORD_LENGTH
                )
            )
        } else if (confirmPassword != password) {
            emit(Resource.Success<Boolean>(false))
            emit(Resource.Error<Boolean>("Confirm password must match password"))
        } else {
            emit(Resource.Success<Boolean>(true))
        }
    }
}