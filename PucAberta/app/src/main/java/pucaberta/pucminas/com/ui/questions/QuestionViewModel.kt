package pucaberta.pucminas.com.ui.questions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import pucaberta.pucminas.com.model.QuestionsFile

class QuestionViewModel(json: String) : ViewModel() {
    val questionsLiveData: MutableLiveData<QuestionResponse> = MutableLiveData()
    private var numberQuestions = 0
    private var currentQuestion = 0
    private var questionFile: QuestionsFile = Gson().fromJson(json, QuestionsFile::class.java)
    var answerChecked: Int = 0

    init {
        numberQuestions = questionFile.size()
        questionsLiveData.value =
                QuestionResponse(number = currentQuestion, question = questionFile.levels[0].questions[currentQuestion])
    }
}
