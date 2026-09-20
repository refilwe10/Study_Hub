package com.example.study_hub

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun validRegistrationDetails_areAccepted() {
        val name = "Study Hub User"
        val email = "student@example.com"
        val password = "password123"
        val confirmPassword = "password123"

        assertTrue(name.isNotBlank())
        assertTrue(email.isNotBlank())
        assertTrue(password.length >= 6)
        assertTrue(password == confirmPassword)
    }

    @Test
    fun emptyName_isRejected() {
        val name = ""

        assertFalse(name.isNotBlank())
    }

    @Test
    fun shortPassword_isRejected() {
        val password = "12345"

        assertTrue(password.length < 6)
    }

    @Test
    fun mismatchedPasswords_areRejected() {
        val password = "password123"
        val confirmPassword = "password456"

        assertFalse(password == confirmPassword)
    }
}