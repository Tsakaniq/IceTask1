package za.co.rbi.lecturer.icetask1

import android.content.Context

class UserStorage(context: Context) {
    private val prefs = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    fun saveUser(username: String, hash: String, salt: String) {
        prefs.edit()
            .putString("username", username)
            .putString("password_hash", hash)
            .putString("password_salt", salt)
            .apply()
    }

    fun getStoredHash(): String? = prefs.getString("password_hash", null)
    fun getStoredSalt(): String? = prefs.getString("password_salt", null)
    fun getStoredUsername(): String? = prefs.getString("username", null)

    fun setLoggedIn(status: Boolean) {
        prefs.edit().putBoolean("is_logged_in", status).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)
}