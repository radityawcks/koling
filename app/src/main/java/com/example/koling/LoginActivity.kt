package com.example.koling

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.koling.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import java.net.URL

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek apakah pengguna sudah login
        checkAlreadyLoggedIn()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.btnLogin.setOnClickListener {
            val loginEmail = binding.etUsername.text.toString().trim()
            val loginPassword = binding.etPassword.text.toString().trim()

            if (loginEmail.isNotEmpty() && loginPassword.isNotEmpty()) {
                loginAdmin(loginEmail, loginPassword)
            } else {
                Toast.makeText(this, "Silakan isi semua informasi yang diperlukan.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun checkAlreadyLoggedIn() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val isAdmin = sharedPreferences.getBoolean("isAdmin", false)

        if (email != null) {
            // Jika pengguna sudah login, arahkan ke halaman admin atau user
            if (isAdmin) {
                startActivity(Intent(this, MainAdmin::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }
    }

    private fun loginAdmin(email: String, password: String) {
        // Cek apakah pengguna adalah admin
        val adminRef = firebaseDatabase.reference.child("admins")
        adminRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (adminSnapshot in snapshot.children) {
                        val adminData = adminSnapshot.getValue(AdminData::class.java)
                        if (adminData != null && adminData.password == password) {
                            Toast.makeText(this@LoginActivity, "Login Admin Sukses", Toast.LENGTH_SHORT).show()
                            saveLoginInfo(email, true, adminData.username, adminData.jabatan, adminData.foto) // Menyimpan username admin
                            startActivity(Intent(this@LoginActivity, MainAdmin::class.java))
                            finish()
                            return
                        }
                    }
                }
                // Jika bukan admin, cek pengguna biasa
                checkRegularUser(email, password)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Database error : ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkRegularUser(email: String, password: String) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(userSnapshot: DataSnapshot) {
                if (userSnapshot.exists()) {
                    for (user in userSnapshot.children) {
                        val userData = user.getValue(UserData::class.java)
                        if (userData != null && userData.password == password) {
                            Toast.makeText(this@LoginActivity, "Login Sukses", Toast.LENGTH_SHORT).show()
                            saveLoginInfo(email, false, userData.username ?: "", "", "") // Menyimpan username pengguna biasa
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                            return
                        }
                    }
                }
                Toast.makeText(this@LoginActivity, "Login Gagal", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Database error : ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Fungsi untuk menyimpan informasi login di SharedPreferences
    private fun saveLoginInfo(email: String, isAdmin: Boolean, username: String, jabatan: String, photoURL: String) {
        sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE) // Perbaikan disini
        val editor = sharedPref?.edit()
        editor?.putString("email", email)
        editor?.putBoolean("isAdmin", isAdmin)
        editor?.putString("username", username) // Menyimpan username
        editor?.putString("jabatan", jabatan)
        editor?.putString("foto", photoURL)
        editor?.apply()
    }

}
