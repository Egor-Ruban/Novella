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
        val username = intent.getStringExtra("username") ?: "missed"
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
        binding.tvKir.text = jsonObject.getString("title").replace("{username}",username)
        val jsonAnswers = jsonObject.getJSONArray("answers")
        val howMuchAnswers = jsonAnswers.length()
        val choices = arrayOf(binding.secondOption, binding.thirdOption, binding.firstOption)
        for(i in choices.indices){
            if(howMuchAnswers > i){
                with(choices[i]) {
                    val jsonAnswer = jsonAnswers.getJSONObject(i)
                    visibility = View.VISIBLE
                    text = jsonAnswer.getString("text")
                    setOnClickListener {
                        val intent = Intent(baseContext, StoryActivity::class.java)
                        intent.putExtra("id", jsonAnswer.getInt("nextID"))
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        Log.d("MY_TAG", "${jsonAnswers.length()}")
    }
}