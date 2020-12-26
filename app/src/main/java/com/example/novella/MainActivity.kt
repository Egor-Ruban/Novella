package com.example.novella

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.StartScreenBinding

private lateinit var binding : StartScreenBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvBtnStart.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }
    }
}