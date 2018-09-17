package pucaberta.pucminas.com.model

import com.google.gson.annotations.SerializedName

class QuestionsFile(@SerializedName("niveis") val levels: Array<Level>)