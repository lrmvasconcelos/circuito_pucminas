package pucaberta.pucminas.com.ui.result

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.result_fragment.*

import pucaberta.pucminas.com.R

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.result_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
        observeScore()
        restartButton.setOnClickListener {
            viewModel.clearData()
            Navigation.findNavController(activity!!, R.id.myFragment)
                    .navigate(R.id.action_resultFragment_to_codeFragment)
        }
    }

    private fun observeScore() {
        viewModel.scoreLiveData.observe(this, Observer {
            resultText.text = String.format(getString(R.string.result), it)
        })
    }

}
