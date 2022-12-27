package com.example.signinbigtest.authorization_feature.data.repository

import com.example.signinbigtest.authorization_feature.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun saveRegistrationDates(name: String, login: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveDateFromAuthServices(name: String, uuid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserNameFromDatabase(): String {
        TODO("Not yet implemented")
    }

    override suspend fun checkAuthorizationDates(login: String, password: String): Boolean {
        TODO("Not yet implemented")
    }
}