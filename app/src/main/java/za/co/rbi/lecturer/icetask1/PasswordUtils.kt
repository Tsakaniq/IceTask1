package za.co.rbi.lecturer.icetask1

import java.security.MessageDigest
import java.security.SecureRandom
import android.util.Base64

object PasswordUtils {

    // Generates a random salt for each user
    fun generateSalt(): String {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return Base64.encodeToString(salt, Base64.NO_WRAP)
    }

    // Hashes the password with the salt using SHA-256
    fun hashPassword(password: String, salt: String): String {
        val saltedPassword = password + salt
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(saltedPassword.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(hashBytes, Base64.NO_WRAP)
    }

    // Verifies a login attempt against the stored hash
    fun verifyPassword(inputPassword: String, storedHash: String, storedSalt: String): Boolean {
        val hashOfInput = hashPassword(inputPassword, storedSalt)
        return hashOfInput == storedHash
    }
}