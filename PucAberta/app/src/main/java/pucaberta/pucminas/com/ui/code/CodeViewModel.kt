package pucaberta.pucminas.com.ui.code

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import pucaberta.pucminas.com.MyApplication
import pucaberta.pucminas.com.model.Level
import pucaberta.pucminas.com.model.QuestionsFile

class CodeViewModel : ViewModel() {
    val questionsLiveData = MutableLiveData<String>()
    private val gson = Gson()

    init {
        if (isGameInProgress())
            questionsLiveData.value = gson.toJson(getQuestions())
    }

    private fun getQuestions(): QuestionsFile? {
        return MyApplication.instance.getQuestions()
    }

    private fun isGameInProgress(): Boolean {
       return MyApplication.instance.isGameInProgress()
    }

    fun getQuestionsJson(myJson: String, code: String): String {
        val firstLevel: Int = code.toInt() % 13
        val secondLevel: Int = code.toInt() % 10
        val thirdLevel: Int = code.toInt() % 11

        val file = gson.fromJson(myJson, QuestionsFile::class.java)
        val easyLevel = Level(type = 0, questions = mutableListOf(
                file.levels[0].questions[firstLevel],
                file.levels[0].questions[firstLevel + 1],
                file.levels[0].questions[firstLevel + 2]
        ))
        val mediumLevel = Level(type = 1, questions = mutableListOf(
                file.levels[1].questions[secondLevel],
                file.levels[1].questions[secondLevel + 1],
                file.levels[1].questions[secondLevel + 2]
        ))
        val hardLevel = Level(type = 2, questions = mutableListOf(
                file.levels[2].questions[thirdLevel],
                file.levels[2].questions[thirdLevel + 1],
                file.levels[2].questions[thirdLevel + 2]
        ))
        return gson.toJson(QuestionsFile(arrayOf(easyLevel, mediumLevel, hardLevel)))
    }
}
