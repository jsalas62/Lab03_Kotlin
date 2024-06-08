package com.salas.jorge.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.salas.jorge.laboratoriocalificado03.api.TeacherResponse
import com.salas.jorge.laboratoriocalificado03.databinding.ActivityMainBinding
import com.salas.jorge.laboratoriocalificado03.util.RetrofitClient
import com.salas.jorge.laboratoriocalificado03.model.Teacher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TeacherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_name)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        loadTeachers()
    }

    private fun loadTeachers() {
        RetrofitClient.apiService.getTeachers().enqueue(object : Callback<TeacherResponse> {
            override fun onResponse(call: Call<TeacherResponse>, response: Response<TeacherResponse>) {
                if (response.isSuccessful) {
                    val teachers = response.body()?.teachers ?: emptyList()
                    adapter = TeacherAdapter(teachers, ::onItemClick, ::onItemLongClick)
                    binding.recyclerView.adapter = adapter
                } else {
                    Log.e("MainActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TeacherResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("MainActivity", "API call failed", t)
            }
        })
    }

    private fun onItemClick(teacher: Teacher) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${teacher.phone_number}")
        }
        startActivity(intent)
    }

    private fun onItemLongClick(teacher: Teacher) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${teacher.email}")
        }
        startActivity(intent)
    }
}
