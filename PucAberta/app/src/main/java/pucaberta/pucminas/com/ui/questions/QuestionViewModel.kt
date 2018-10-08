package pucaberta.pucminas.com.ui.questions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.CountDownTimer
import com.google.gson.Gson
import pucaberta.pucminas.com.MyApplication
import pucaberta.pucminas.com.model.Question
import pucaberta.pucminas.com.model.QuestionResponse
import pucaberta.pucminas.com.model.QuestionsFile

class QuestionViewModel(json: String) : ViewModel() {

    val questionsLiveData: MutableLiveData<Question> = MutableLiveData()
    val btnStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val timeLiveData: MutableLiveData<String> = MutableLiveData()
    val correctAnswerLiveData: MutableLiveData<QuestionResponse> = MutableLiveData()
    val scoreLiveData: MutableLiveData<Int> = MutableLiveData()
    val countDownTimer = timer(32000, 1000)
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
            countDownTimer.start()
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
        countDownTimer.cancel()
        if (questionsList!![currentQuestion].answers[answerChecked].correct) {
            correctAnswers++
            isCorrect = true
        }
        currentQuestion++
        if (currentQuestion < 9) {
            this.answerChecked = -1
            correctAnswerLiveData.value =
                    QuestionResponse(currentQuestion, questionsList!![currentQuestion], isCorrect)
            countDownTimer.start()
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

    private fun timer(millisInFuture:Long,countDownInterval:Long):CountDownTimer{
        return object: CountDownTimer(millisInFuture,countDownInterval){
            override fun onTick(millisUntilFinished: Long){
                timeLiveData.value = formattedTime(millisUntilFinished)
            }

            override fun onFinish() {
                currentQuestion++
                if (currentQuestion < 9) {
                    answerChecked = -1
                    correctAnswerLiveData.value = QuestionResponse(
                            currentQuestion,
                            questionsList!![currentQuestion],
                            false)
                    countDownTimer.start()
                    btnStateLiveData.value = false
                } else {
                    correctAnswerLiveData.value = QuestionResponse(
                            9,
                            questionsList!![8],
                            false)
                    finishQuestions()
                }
                saveDataInstance()
            }
        }
    }

    private fun formattedTime(millisUntilFinished: Long): String? {
        val seconds = millisUntilFinished / 1000
        return if(seconds > 10)
            "0:$seconds"
        else
            "0:0$seconds"
    }
}
