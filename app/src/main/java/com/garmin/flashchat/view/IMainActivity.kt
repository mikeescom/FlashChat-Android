package com.garmin.flashchat.view

interface IMainActivity {
    fun registerUser(email: String, password: String)
    fun loginUser(email: String, password: String)
    fun navigateToRegister()
    fun navigateToLogin()
}