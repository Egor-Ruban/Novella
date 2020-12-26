package com.example.novella

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.StartScreenBinding

private lateinit var binding : StartScreenBinding
class EndActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = StartScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = getString(R.string.end_title)
        binding.tvBtnStart.text = getString(R.string.end_continue)
        binding.tvBtnStart.setOnClickListener {
            finish()
        }
    }
}