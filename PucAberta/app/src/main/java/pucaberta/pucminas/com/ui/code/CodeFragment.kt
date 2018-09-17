package pucaberta.pucminas.com.ui.code

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.code_fragment.*
import pucaberta.pucminas.com.R

class CodeFragment : Fragment() {

    companion object {
        fun newInstance() = CodeFragment()
    }

    private lateinit var viewModel: CodeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.code_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CodeViewModel::class.java)

        btnNext.setOnClickListener {
            if (checkCode()) {
                val bundle = Bundle()
                bundle.putString("questions", viewModel.getQuestionsJson())
                Navigation.findNavController(activity!!, R.id.myFragment)
                        .navigate(R.id.action_codeFragment_to_questionFragment, bundle)
            }
        }
    }

    private fun checkCode(): Boolean {
        return true
    }

}
