package pucaberta.pucminas.com.model

import com.google.gson.annotations.SerializedName

class QuestionsFile(@SerializedName("niveis") val levels: Array<Level>){

    fun size() : Int{
        var count = 0
        for (level in levels) for (question in level.questions) count++
        return count
    }
}