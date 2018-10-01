package pucaberta.pucminas.com.ui.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_info.*
import pucaberta.pucminas.com.BuildConfig
import pucaberta.pucminas.com.R

class InfoFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val versionName = BuildConfig.VERSION_NAME
        appVersionTxt.text = versionName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                InfoFragment().apply {}
    }
}
