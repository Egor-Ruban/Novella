package com.example.novella

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.EdgeScreenBinding

private lateinit var binding : EdgeScreenBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EdgeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.tvTitle.text = getString(R.string.start_title)
        binding.tvContinue.text = getString(R.string.start_continue)
        binding.tvContinue.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }
    }
}