package com.example.psychoremastered.presentation.choose.google_auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.psychoremastered.domain.model.SignInResult
import com.example.psychoremastered.domain.model.UserData
import com.example.psychoremstered.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

class GoogleAuthUiClient(
    private val context: Context,
) {
    suspend fun signIn(): SignInResult {
        try {
            val auth = Firebase.auth
            val credentialManager = CredentialManager.create(context)

            val result = credentialManager.getCredential(
                request = getCredentialRequest(),
                context = context,
            )

            val googleIdToken = GoogleIdTokenCredential.createFrom(result.credential.data)
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken.idToken, null)
            val user = auth.signInWithCredential(firebaseCredential).await().user

            return SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        displayName = displayName,
                        profilePictureUri = photoUrl.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            return SignInResult(
                data = null,
                errorMessage = e.localizedMessage
            )
        }
    }

    private fun getGoogleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(true)
            .setNonce(getHashedNonce(rawNonce = UUID.randomUUID().toString()))
            .build()
    }

    private fun getHashedNonce(rawNonce: String): String {
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance(context.getString(R.string.message_digest_algorithm))
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    private fun getCredentialRequest(): GetCredentialRequest {
        return GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption())
            .build()
    }
}