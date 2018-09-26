package pucaberta.pucminas.com.ui.questions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import pucaberta.pucminas.com.MyApplication
import pucaberta.pucminas.com.model.Question
import pucaberta.pucminas.com.model.QuestionResponse
import pucaberta.pucminas.com.model.QuestionsFile

class QuestionViewModel(json: String) : ViewModel() {

    val questionsLiveData: MutableLiveData<Question> = MutableLiveData()
    val btnStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val correctAnswerLiveData: MutableLiveData<QuestionResponse> = MutableLiveData()
    val scoreLiveData: MutableLiveData<Int> = MutableLiveData()
    private var numberQuestions = 0
    private var currentQuestion = getCurrentQuestion()
    private var correctAnswers = getScore()
    private var questionFile: QuestionsFile? = Gson().fromJson(json, QuestionsFile::class.java)
    private var questionsList = questionFile?.allQuestions()
    private var answerChecked: Int = -1

    init {
        if (isQuestionFinished()) {
           scoreLiveData.value = 10
        } else{
            numberQuestions = questionFile!!.size()
            questionsLiveData.value = questionsList!![currentQuestion]
            btnStateLiveData.value = false
            saveDataInstance()
            startQuestions()
        }
    }

    fun checked(answer: Int) {
        answerChecked = if (answerChecked == answer) -1 else answer
        btnStateLiveData.value = answerChecked != -1
    }

    fun nextQuestion() {
        if (answerChecked != -1) {
            checkQuestion(answerChecked)
        }
    }

    private fun checkQuestion(answerChecked: Int) {
        var isCorrect = false
        if (questionsList!![currentQuestion].answers[answerChecked].correct) {
            correctAnswers++
            isCorrect = true
        }
        currentQuestion++
        if (currentQuestion < 9) {
            this.answerChecked = -1
            correctAnswerLiveData.value =
                    QuestionResponse(currentQuestion, questionsList!![currentQuestion], isCorrect)
            btnStateLiveData.value = false
        } else {
            correctAnswerLiveData.value = QuestionResponse(9, questionsList!![8], isCorrect)
            finishQuestions()
        }
        saveDataInstance()
    }

    private fun saveDataInstance() {
        MyApplication.instance.saveScore(correctAnswers)
        MyApplication.instance.saveQuestions(questionFile!!)
        MyApplication.instance.saveCurrentQuestion(currentQuestion)
        MyApplication.instance.saveGameProgress(true)
    }

    private fun getCurrentQuestion(): Int {
        return MyApplication.instance.getCurrentQuestion()
    }

    private fun isQuestionFinished(): Boolean {
        return MyApplication.instance.isQuestionsFinished()
    }

    private fun startQuestions() {
        MyApplication.instance.startQuestions()
    }

    private fun finishQuestions() {
        MyApplication.instance.finishQuestions()
    }

    private fun getScore(): Int {
        val score = MyApplication.instance.getScore()
        return if (score == -1)
            0
        else
            score
    }
}
