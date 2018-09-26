package pucaberta.pucminas.com.ui.questions

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.github.florent37.viewanimator.ViewAnimator
import kotlinx.android.synthetic.main.question_fragment.*
import pucaberta.pucminas.com.R
import pucaberta.pucminas.com.model.Question
import pucaberta.pucminas.com.model.QuestionResponse
import pucaberta.pucminas.com.ui.ProgressBarAnim

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
        progressBar.max = 9

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
        viewModel.questionsLiveData.observe(this, Observer {
            showQuestion(it, 0)
        })
        viewModel.btnStateLiveData.observe(this, Observer {
            setButtonState(it)
        })
        viewModel.correctAnswerLiveData.observe(this, Observer {
            animateQuestions(it!!)
        })
        viewModel.scoreLiveData.observe(this, Observer {
            Navigation.findNavController(activity!!, R.id.myFragment)
                    .navigate(R.id.action_questionFragment_to_resultFragment)
        })
    }

    private fun animateQuestions(response: QuestionResponse) {
        fadeOut()
        if (response.isCorrect) {
            correctAnimation.visibility = View.VISIBLE
            correctAnimation.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {}
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationStart(p0: Animator?) {}
                override fun onAnimationEnd(p0: Animator?) {
                    Handler().postDelayed({
                        correctAnimation.visibility = View.GONE
                        if (response.number == 9) {
                            Navigation.findNavController(activity!!, R.id.myFragment)
                                    .navigate(R.id.action_questionFragment_to_resultFragment)
                        } else {
                            showQuestion(response.question, response.number)
                            fadeIn()
                        }
                    }, 500)
                }
            })
            correctAnimation.playAnimation()
        } else {
            wrongAnimation.visibility = View.VISIBLE
            wrongAnimation.playAnimation()
            wrongAnimation.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {}
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationStart(p0: Animator?) {}
                override fun onAnimationEnd(p0: Animator?) {
                    Handler().postDelayed({
                        wrongAnimation.visibility = View.GONE
                        if (response.number == 9) {
                            Navigation.findNavController(activity!!, R.id.myFragment)
                                    .navigate(R.id.action_questionFragment_to_resultFragment)
                        } else {
                            showQuestion(response.question, response.number)
                            fadeIn()
                        }
                    }, 500)
                }
            })
        }


    }

    private fun fadeIn() {
        questionsContainer.visibility = View.VISIBLE
        ViewAnimator
                .animate(questionsContainer)
                .fadeIn()
                .duration(300)
                .start()
    }

    private fun fadeOut() {
        ViewAnimator
                .animate(questionsContainer)
                .fadeOut()
                .duration(300)
                .onStop { questionsContainer.visibility = View.GONE }
                .start()
    }

    private fun setButtonState(isEnabled: Boolean?) {
        btnAnswer.enabled(isEnabled)
    }

    private fun showQuestion(data: Question?, number: Int) {
        val animation = ProgressBarAnim(progressBar, 1000)
        animation.setProgress(number)
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
