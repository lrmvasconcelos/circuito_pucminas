package pucaberta.pucminas.com.ui.result

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import pucaberta.pucminas.com.MyApplication

class ResultViewModel : ViewModel() {
    val scoreLiveData: MutableLiveData<Int> = MutableLiveData()
    init {
        scoreLiveData.value = getScore()
    }

    private fun getScore() :Int{
        return MyApplication.instance.getScore()
    }

    fun clearData() {
        MyApplication.instance.clearData()
    }
}
