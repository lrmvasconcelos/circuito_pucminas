package pucaberta.pucminas.com.ui.questions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.question_fragment.*

import pucaberta.pucminas.com.R
import pucaberta.pucminas.com.model.Question

class QuestionFragment : Fragment() {


    private lateinit var viewModel: QuestionViewModel

    private lateinit var questions: Array<Question>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val questionsJson = arguments?.getString("questions")

        questions = Gson().fromJson(questionsJson, Array<Question>::class.java)
        viewModel = ViewModelProviders.of(this, QuestionViewModelFactory(questionsJson!!))
                .get(QuestionViewModel::class.java)

        observeQuestions()

        checkBoxA.setOnClickListener {
            answerChecked(0)
        }

        checkBoxB.setOnClickListener {
            answerChecked(1)
        }

        checkBoxC.setOnClickListener {
            answerChecked(2)
        }

        checkBoxD.setOnClickListener {
            answerChecked(3)
        }

    }

    private fun observeQuestions() {
        viewModel.questionsLiveData.observe(this, Observer { response ->
            showQuestion(response?.question, response?.number!!)
        })
    }

    private fun showQuestion(data: Question?, number: Int) {
        questionNumber.text = String.format(getString(R.string.question_title), number+1)
        questionText.text = data?.question
        answerAText.text = data?.answers!![0].answer
        answerBText.text = data.answers[1].answer
        answerCText.text = data.answers[2].answer
        answerDText.text = data.answers[3].answer
    }

    fun answerChecked(answerNumber: Int) {
        when {
            answerNumber == 0 -> {
                viewModel.answerChecked = 0
                if (checkBoxB.isChecked)
                    checkBoxB.toggle()
                if (checkBoxC.isChecked)
                    checkBoxC.toggle()
                if (checkBoxD.isChecked)
                    checkBoxD.toggle()
            }
            answerNumber == 1 -> {
                viewModel.answerChecked = 1
                if (checkBoxA.isChecked)
                    checkBoxA.toggle()
                if (checkBoxC.isChecked)
                    checkBoxC.toggle()
                if (checkBoxD.isChecked)
                    checkBoxD.toggle()
            }
            answerNumber == 2 -> {
                viewModel.answerChecked = 2
                if (checkBoxA.isChecked)
                    checkBoxA.toggle()
                if (checkBoxB.isChecked)
                    checkBoxB.toggle()
                if (checkBoxD.isChecked)
                    checkBoxD.toggle()
            }
            answerNumber == 3 -> {
                viewModel.answerChecked = 3
                if (checkBoxA.isChecked)
                    checkBoxA.toggle()
                if (checkBoxB.isChecked)
                    checkBoxB.toggle()
                if (checkBoxC.isChecked)
                    checkBoxC.toggle()
            }
        }
    }
}
