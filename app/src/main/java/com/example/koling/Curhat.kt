package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koling.adapter.CurhatAdapter
import com.example.koling.databinding.ActivityCurhatBinding
import com.example.koling.model.Admin
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Curhat : AppCompatActivity() {

    private lateinit var binding: ActivityCurhatBinding
    private lateinit var curhatAdapter: CurhatAdapter
    private val adminList = mutableListOf<Admin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurhatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        curhatAdapter = CurhatAdapter(adminList)
        binding.recyclerView.adapter = curhatAdapter

        // Memuat data admin dari Firebase
        loadAdminData()

        binding.btnBack.setOnClickListener{
            gotoMain()
        }
    }

    private fun gotoMain(){
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun loadAdminData() {
        val database = FirebaseDatabase.getInstance().getReference("admins")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adminList.clear()
                for (dataSnapshot in snapshot.children) {
                    val admin = dataSnapshot.getValue(Admin::class.java)
                    if (admin != null) {
                        adminList.add(admin)
                    }
                }
                curhatAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Tangani error
            }
        })
    }
}