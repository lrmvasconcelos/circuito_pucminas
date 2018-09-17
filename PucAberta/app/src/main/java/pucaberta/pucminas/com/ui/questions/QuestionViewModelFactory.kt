package pucaberta.pucminas.com.ui.questions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class QuestionViewModelFactory(private val questionJson: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionViewModel(questionJson) as T
    }


}