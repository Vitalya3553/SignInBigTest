package com.example.signinbigtest.authorization_feature.domain.repository

interface AuthRepository {

    suspend fun saveRegistrationDates(name: String, login: String, password : String)

    suspend fun saveDateFromAuthServices(name: String,uuid: String)

    suspend fun getUserNameFromDatabase():String

    suspend fun checkAuthorizationDates(login: String, password : String): Boolean
}