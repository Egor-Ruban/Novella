package com.example.novella

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.novella.databinding.ActivityStoryBinding
import org.json.JSONArray
import org.json.JSONObject

class StoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //todo поменять ключи на константу
        val username = intent.getStringExtra("username") ?: getString(R.string.value_missed)
        val currentId = intent.getIntExtra("id", -1)
        val currentStepJSON = getCurrentStepInfo(currentId)
        updateLayout(currentStepJSON, username)
    }

    private fun getCurrentStepInfo(currentID: Int) : JSONObject{
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

    private fun updateLayout(currentStepJSON: JSONObject, username: String){
        val isKirImageNeeded = currentStepJSON.getBoolean("showKir") //todo вынести ключ в константу
        if(isKirImageNeeded){
            binding.ivStoryKir.visibility = View.VISIBLE
        }
        val pic = currentStepJSON.getString("background")//todo вынести ключ в константу
        val context = binding.ivStoryBackground.context
        val id = context.resources
            .getIdentifier(pic, "drawable", context.packageName)
        binding.ivStoryBackground.setImageDrawable(getDrawable(id))
        binding.tvStoryTitle.text = currentStepJSON.getString("title")//todo вынести ключ в константу
            .replace("{username}", username)
        val jsonAnswers = currentStepJSON.getJSONArray("answers")//todo вынести ключ в константу
        val howMuchAnswers = jsonAnswers.length()
        val choices = arrayOf(binding.secondOption, binding.thirdOption, binding.firstOption)
        for(i in choices.indices){
            if(howMuchAnswers > i){
                val jsonAnswer = jsonAnswers.getJSONObject(i)
                with(choices[i]) {
                    visibility = View.VISIBLE
                    text = jsonAnswer.getString("text")//todo вынести ключ в константу
                    setOnClickListener {
                        val nextID = jsonAnswer.getInt("nextID")//todo вынести ключ в константу
                        val intent = when(nextID){
                            -1 -> Intent(baseContext, EndActivity::class.java)
                            else -> Intent(baseContext, StoryActivity::class.java)
                                    .putExtra("id", jsonAnswer.getInt("nextID")) //todo вынести ключ в константу
                        }
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}