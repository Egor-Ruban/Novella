package com.example.novella

import android.content.Intent
import android.os.Bundle
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
        binding.tvIntroContinue.setOnClickListener {
            val name = binding.etName.text.toString()
            if(name == ""){
                Toast.makeText(
                        this,
                        getString(R.string.empty_name_entered),
                        Toast.LENGTH_SHORT)
                        .show()
            } else {
                val intent = Intent(this, StoryActivity::class.java)
                intent.putExtra(NovellaConst.CTX_VALUE_ID, 3)
                intent.putExtra(NovellaConst.CTX_VALUE_USERNAME, name)
                startActivity(intent)
                finish()
            }
        }
    }

}