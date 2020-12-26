package com.example.novella

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.ActivityStoryBinding
import org.json.JSONArray
import org.json.JSONObject

private lateinit var binding : ActivityStoryBinding
class StoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val username = intent.getStringExtra("username") ?: "missed"
        val currentId = intent.getIntExtra("id", -1)
        val currentStepJSON = getCurrentStepInfo(currentId)
        updateLayout(currentStepJSON, username)
    }

    private fun getCurrentStepInfo(currentID : Int) : JSONObject{
        val jsonStr = resources.openRawResource(R.raw.scenario)
            .bufferedReader().use { it.readText() }
        val jsonArr = JSONArray(jsonStr)
        var jsonObject = jsonArr.getJSONObject(0)
        for(i in 0 until jsonArr.length()){
            jsonObject = jsonArr.getJSONObject(i)
            if(jsonObject.getInt("id") == currentID){
                break
            }
        }
        return jsonObject
    }

    private fun updateLayout(currentStepJSON : JSONObject, username : String){
        binding.tvKir.text = currentStepJSON.getString("title")
            .replace("{username}", username)
        val jsonAnswers = currentStepJSON.getJSONArray("answers")
        val howMuchAnswers = jsonAnswers.length()
        val choices = arrayOf(binding.secondOption, binding.thirdOption, binding.firstOption)
        for(i in choices.indices){
            if(howMuchAnswers > i){
                with(choices[i]) {//todo добавить обновление картинок
                    val jsonAnswer = jsonAnswers.getJSONObject(i)
                    visibility = View.VISIBLE
                    text = jsonAnswer.getString("text")
                    setOnClickListener {
                        var intent = Intent(baseContext, EndActivity::class.java)
                        if(jsonAnswer.getInt("nextID") != 14){
                            intent = Intent(baseContext, StoryActivity::class.java)
                            intent.putExtra("id", jsonAnswer.getInt("nextID"))
                        }
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}