package pucaberta.pucminas.com.ui.questions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.gson.Gson
import kotlinx.android.synthetic.main.question_fragment.*

import pucaberta.pucminas.com.R
import pucaberta.pucminas.com.model.Question

class QuestionFragment : Fragment() {


    private lateinit var viewModel: QuestionViewModel

//    private lateinit var questions: Array<Question>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val questionsJson = arguments?.getString("questions")

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

        btnAnswer.setOnClickListener {
            viewModel.nextQuestion()
            uncheckAnswers()
        }
    }

    private fun uncheckAnswers() {
        if (checkBoxA.isChecked)
            checkBoxA.toggle()
        if (checkBoxB.isChecked)
            checkBoxB.toggle()
        if (checkBoxC.isChecked)
            checkBoxC.toggle()
        if (checkBoxD.isChecked)
            checkBoxD.toggle()
    }

    private fun observeQuestions() {
        viewModel.questionsLiveData.observe(this, Observer { response ->
            showQuestion(response?.question, response?.number!!)
        })
        viewModel.btnStateLiveData.observe(this, Observer {
            setButtonState(it)
        })
        viewModel.scoreLiveData.observe(this, Observer {
            Navigation.findNavController(activity!!, R.id.myFragment)
                    .navigate(R.id.action_questionFragment_to_resultFragment)
        })
    }

    private fun setButtonState(isEnabled: Boolean?) {
        btnAnswer.enabled(isEnabled)
    }

    private fun showQuestion(data: Question?, number: Int) {
        questionNumber.text = String.format(getString(R.string.question_title), number+1)
        questionText.text = data?.question
        answerAText.text = data?.answers!![0].answer
        answerBText.text = data.answers[1].answer
        answerCText.text = data.answers[2].answer
        answerDText.text = data.answers[3].answer
    }

    private fun answerChecked(answerNumber: Int) {
        when (answerNumber) {
            0 -> {
                viewModel.checked(0)
                if (checkBoxB.isChecked)
                    checkBoxB.toggle()
                if (checkBoxC.isChecked)
                    checkBoxC.toggle()
                if (checkBoxD.isChecked)
                    checkBoxD.toggle()
            }
            1 -> {
                viewModel.checked(1)
                if (checkBoxA.isChecked)
                    checkBoxA.toggle()
                if (checkBoxC.isChecked)
                    checkBoxC.toggle()
                if (checkBoxD.isChecked)
                    checkBoxD.toggle()
            }
            2 -> {
                viewModel.checked(2)
                if (checkBoxA.isChecked)
                    checkBoxA.toggle()
                if (checkBoxB.isChecked)
                    checkBoxB.toggle()
                if (checkBoxD.isChecked)
                    checkBoxD.toggle()
            }
            3 -> {
                viewModel.checked(3)
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

private fun Button.enabled(enabled: Boolean?) {
    if (enabled!!)
        this.setBackgroundColor(resources.getColor(R.color.buttonEnabled))
    else
        this.setBackgroundColor(resources.getColor(R.color.buttonDisabled))
}
