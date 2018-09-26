package pucaberta.pucminas.com.ui.code

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.code_fragment.*
import pucaberta.pucminas.com.R
import java.io.IOException
import java.io.InputStream

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
        observeViewModel()
        btnNext.setOnClickListener {
            if (checkCode()) {
                val bundle = Bundle()
                val myJson = inputStreamToString(resources.openRawResource(R.raw.questions))
                bundle.putString("questions", viewModel.getQuestionsJson(myJson))
                Navigation.findNavController(activity!!, R.id.myFragment)
                        .navigate(R.id.action_codeFragment_to_questionFragment, bundle)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.questionsLiveData.observe(this, Observer {
            val bundle = Bundle()
            bundle.putString("questions", it)
            Navigation.findNavController(activity!!, R.id.myFragment)
                    .navigate(R.id.action_codeFragment_to_questionFragment, bundle)

        })
    }

    private fun checkCode(): Boolean {
        return true
    }

    private fun inputStreamToString(inputStream: InputStream): String {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            ""
        }

    }

}
