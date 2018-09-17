package pucaberta.pucminas.com.ui.code

import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import pucaberta.pucminas.com.model.Answer
import pucaberta.pucminas.com.model.Question

class CodeViewModel : ViewModel() {

    fun getQuestionsJson(): String {
        val questions = arrayOf(Question(question = "Quanto e 2 + 2?",
                answers = arrayOf(Answer(correct = false, answer = "1"),
                        Answer(correct = false, answer = "2"),
                        Answer(correct = false, answer = "3"),
                        Answer(correct = true, answer = "4"),
                        Answer(correct = false, answer = "5"))),
                Question(question = "Qual a capital do Brasil?",
                        answers = arrayOf(Answer(correct = false, answer = "Belo Horizonte"),
                                Answer(correct = false, answer = "São Paulo"),
                                Answer(correct = false, answer = "Rio de Janeiro"),
                                Answer(correct = false, answer = "Salvador"),
                                Answer(correct = true, answer = "Brasilia"))))
        val gson = Gson()
        return gson.toJson(questions)
    }
}
