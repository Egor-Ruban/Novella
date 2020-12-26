package com.example.novella

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.ActivityStoryBinding
import org.json.JSONArray


private lateinit var binding : ActivityStoryBinding
class StoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //
        //reading whole scenario from from json file
        //
        val jsonStr = resources.openRawResource(R.raw.scenario)
            .bufferedReader().use { it.readText() }
        val jsonArr = JSONArray(jsonStr)
        val currentId = intent.getIntExtra("id", -1)
        var jsonObject = jsonArr.getJSONObject(0)
        for(i in 0 until jsonArr.length()){
            jsonObject = jsonArr.getJSONObject(i)
            if(jsonObject.getInt("id") == currentId){
                break
            }
        }
        Log.d("MY_TAG", jsonObject.getString("title"))
        binding.tvKir.text = jsonObject.getString("title")
        val jsonAnswers = jsonObject.getJSONArray("answers")
        val howMuchAnswers = jsonAnswers.length()
        binding.secondOption.visibility = View.VISIBLE
        binding.secondOption.text = jsonAnswers.getJSONObject(0).getString("text")
        binding.secondOption.setOnClickListener {
            val intent = Intent(this, StoryActivity::class.java)
            intent.putExtra("id", jsonAnswers.getJSONObject(0).getInt("nextID"))
            startActivity(intent)
            finish()
        }
        if(howMuchAnswers > 1){
            binding.thirdOption.visibility = View.VISIBLE
            binding.thirdOption.text = jsonAnswers.getJSONObject(1).getString("text")
            binding.thirdOption.setOnClickListener {
                val intent = Intent(this, StoryActivity::class.java)
                intent.putExtra("id", jsonAnswers.getJSONObject(1).getInt("nextID"))
                startActivity(intent)
                finish()
            }
        }
        if(howMuchAnswers > 2){
            binding.firstOption.visibility = View.VISIBLE
            binding.firstOption.text = jsonAnswers.getJSONObject(2).getString("text")
            binding.firstOption.setOnClickListener {
                val intent = Intent(this, StoryActivity::class.java)
                intent.putExtra("id", jsonAnswers.getJSONObject(2).getInt("nextID"))
                startActivity(intent)
                finish()
            }
        }
        Log.d("MY_TAG", "${jsonAnswers.length()}")
    }
}