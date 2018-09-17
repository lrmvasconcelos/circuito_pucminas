package pucaberta.pucminas.com.ui.questions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import pucaberta.pucminas.com.model.Question

class QuestionViewModel(json: String) : ViewModel() {
    val questionsLiveData: MutableLiveData<QuestionResponse> = MutableLiveData()
    private  var numberQuestions = 0
    private var currentQuestion = 0
    private var questions: Array<Question> = Gson().fromJson(json, Array<Question>::class.java)
    var answerChecked: Int = 0

    init {
        numberQuestions = questions.size
        questionsLiveData.value =
                QuestionResponse(number = currentQuestion, question =questions[currentQuestion])
    }
}
