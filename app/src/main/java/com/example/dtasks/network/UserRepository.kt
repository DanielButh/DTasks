package com.example.dtasks.network

import com.example.dtasks.core.ResultWrapper
import com.example.dtasks.core.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun register(email: String, password: String): ResultWrapper<FirebaseUser> = safeCall {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        result.user ?: throw Exception("No se pudo crear el usuario")
    }

    suspend fun login(email: String, password: String): ResultWrapper<FirebaseUser> = safeCall {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user ?: throw Exception("Usuario no encontrado")
    }
}