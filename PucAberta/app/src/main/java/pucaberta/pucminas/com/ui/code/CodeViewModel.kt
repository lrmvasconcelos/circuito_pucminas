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
        val firstQuestion: Int = code.toInt() % 13
        val secondQuestion: Int = if (firstQuestion > 9) 0 else firstQuestion + 1

        val file = gson.fromJson(myJson, QuestionsFile::class.java)
        val easyLevel = Level(type = 0, questions = mutableListOf(
                file.levels[0].questions[firstQuestion],
                file.levels[0].questions[firstQuestion + 1],
                file.levels[0].questions[firstQuestion + 2]
        ))
        val mediumLevel = Level(type = 1, questions = mutableListOf(
                file.levels[1].questions[secondQuestion],
                file.levels[1].questions[secondQuestion + 1],
                file.levels[1].questions[secondQuestion + 2]
        ))
        val hardLevel = Level(type = 2, questions = mutableListOf(
                file.levels[2].questions[firstQuestion],
                file.levels[2].questions[secondQuestion],
                file.levels[2].questions[secondQuestion + 1]
        ))
        return gson.toJson(QuestionsFile(arrayOf(easyLevel, mediumLevel, hardLevel)))
    }
}
