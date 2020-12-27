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
        val username = intent.getStringExtra(NovellaConst.CTX_VALUE_USERNAME) ?: getString(R.string.value_missed)
        val currentId = intent.getIntExtra(NovellaConst.CTX_VALUE_ID, -1)
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
            if(jsonObject.getInt(NovellaConst.JSON_VALUE_CURRENT_ID) == currentID){
                break
            }
        }
        return jsonObject
    }

    private fun updateLayout(currentStepJSON: JSONObject, username: String){
        //setting background picture
        val backgroundName = currentStepJSON.getString(NovellaConst.JSON_VALUE_BACKGROUND)
        val backgroundID = baseContext.resources
            .getIdentifier(backgroundName, "drawable", baseContext.packageName)
        binding.ivStoryBackground.setImageDrawable(getDrawable(backgroundID))

        //adding Kir image if needed
        val isKirImageNeeded = currentStepJSON.getBoolean(NovellaConst.JSON_VALUE_SHOW_KIR)
        if(isKirImageNeeded){
            binding.ivStoryKir.visibility = View.VISIBLE
        }

        //adding question or information about current step
        binding.tvStoryTitle.text = currentStepJSON.getString(NovellaConst.JSON_VALUE_TITLE)
            .replace(NovellaConst.JSON_VAR_USERNAME, username)

        //adding answer options
        val jsonAnswers = currentStepJSON.getJSONArray(NovellaConst.JSON_ARRAY_ANSWERS)
        setupOptions(jsonAnswers)
    }

    private fun setupOptions(jsonAnswers : JSONArray){
        val howMuchAnswers = jsonAnswers.length()
        val optionViews = arrayOf(binding.secondOption, binding.thirdOption, binding.firstOption)
        for(i in optionViews.indices){
            if(howMuchAnswers > i){
                val jsonAnswer = jsonAnswers.getJSONObject(i)
                with(optionViews[i]) {
                    visibility = View.VISIBLE
                    text = jsonAnswer.getString(NovellaConst.JSON_VALUE_ANSWER_TEXT)
                    setOnClickListener {
                        val nextStepID = jsonAnswer.getInt(NovellaConst.JSON_VALUE_NEXT_ID)
                        val intent = when(nextStepID){
                            -1 -> Intent(baseContext, EndActivity::class.java)
                            else -> Intent(baseContext, StoryActivity::class.java)
                                    .putExtra(NovellaConst.CTX_VALUE_ID, nextStepID)
                        }
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}