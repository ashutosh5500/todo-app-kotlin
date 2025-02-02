package com.ashu.test

//import javax.inject.Inject

class UserRegistrationService (
    private val userRepository: UserRepository,
    private val emailService: EmailService
) {
    fun registerUser(email: String, password: String) {
        userRepository.saveUser(email, password)
        emailService.sendEmail(email, "Welcome to our app")
    }

}