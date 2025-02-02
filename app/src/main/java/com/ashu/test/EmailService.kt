package com.ashu.test

//import javax.inject.Inject

class EmailService  {
    fun sendEmail(email: String, message: String) {
        println("Sending email to $email with message: $message")
    }

}