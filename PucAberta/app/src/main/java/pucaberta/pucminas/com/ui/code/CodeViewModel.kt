package pucaberta.pucminas.com.ui.code

import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import pucaberta.pucminas.com.model.Level
import pucaberta.pucminas.com.model.QuestionsFile

class CodeViewModel : ViewModel() {

    fun getQuestionsJson(myJson: String): String {
        val gson = Gson()
        val file = gson.fromJson(myJson, QuestionsFile::class.java)
        val easyLevel = Level(type = 0, questions = arrayOf(
                file.levels[0].questions[0],
                file.levels[0].questions[1],
                file.levels[0].questions[2]
        ))
        val mediumLevel = Level(type = 0, questions = arrayOf(
                file.levels[1].questions[0],
                file.levels[1].questions[1],
                file.levels[1].questions[2]
        ))
        val hardLevel = Level(type = 0, questions = arrayOf(
                file.levels[2].questions[0],
                file.levels[2].questions[1],
                file.levels[2].questions[2]
        ))
        return gson.toJson(QuestionsFile(arrayOf(easyLevel, mediumLevel, hardLevel)))
    }
}
