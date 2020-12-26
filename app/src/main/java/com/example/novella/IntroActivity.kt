package com.example.novella

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.ActivityIntroductionBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroductionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.tvIntroContinue.setOnClickListener { //todo поменять ключи на константу
            val name = binding.etName.text.toString()
            if(name == ""){
                Toast.makeText(this,"you should write your name", Toast.LENGTH_SHORT)
                        .show()
            } else {
                val intent = Intent(this, StoryActivity::class.java)
                intent.putExtra("id", 3)
                Log.d("MY_TAG", binding.etName.text.toString())
                intent.putExtra("username", name)
                startActivity(intent)
                finish()
            }
        }
    }

}