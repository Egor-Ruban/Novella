package com.example.novella

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.EdgeScreenBinding

class EndActivity : AppCompatActivity() {
    private lateinit var binding : EdgeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EdgeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = getString(R.string.end_title)
        binding.tvContinue.text = getString(R.string.end_continue)
        binding.tvContinue.setOnClickListener {
            finish()
        }
    }
}