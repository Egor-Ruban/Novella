package com.example.novella

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.ActivityIntroductionBinding

private lateinit var binding : ActivityIntroductionBinding
class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.tvName.setOnClickListener {
            val intent = Intent(this, StoryActivity::class.java)
            intent.putExtra("id", 3)
            startActivity(intent)
            finish()
        }
    }

}