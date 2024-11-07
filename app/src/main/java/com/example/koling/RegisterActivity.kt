package com.example.koling

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.example.koling.databinding.ActivityRegisterBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.btnRegister.setOnClickListener {
            val signupEmail = binding.etEmail.text.toString()
            val signupUsername = binding.etusername.text.toString()
            val signupPassword = binding.etPassword.text.toString()

            if (signupPassword.isNotEmpty() && signupUsername.isNotEmpty() && signupEmail.isNotEmpty()) {
                signup(signupEmail, signupUsername, signupPassword)
            }else {
                Toast.makeText(this, "Silakan isi semua informasi yang diperlukan.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLogin.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun signup(email: String, username: String, password: String) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    val id = databaseReference.push().key ?: return
                    val userData = UserData(id, email, username, password)

                    databaseReference.child(id).setValue(userData).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@RegisterActivity, "Register Sukses", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Email yang anda masukkan sudah terdaftar, masukkan email lain!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RegisterActivity, "Database error : ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}