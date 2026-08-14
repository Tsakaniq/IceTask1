package za.co.rbi.lecturer.icetask1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var userStorage: UserStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        userStorage = UserStorage(this)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            handleLogin()
        }

        findViewById<TextView>(R.id.tvGoToRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun handleLogin() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            return
        }

        val storedUsername = userStorage.getStoredUsername()
        val storedHash = userStorage.getStoredHash()
        val storedSalt = userStorage.getStoredSalt()

        if (storedUsername == null || storedHash == null || storedSalt == null) {
            Toast.makeText(this, "No account found. Please register first.", Toast.LENGTH_SHORT).show()
            return
        }

        if (username == storedUsername &&
            PasswordUtils.verifyPassword(password, storedHash, storedSalt)) {

            userStorage.setLoggedIn(true)
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
}