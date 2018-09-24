package pucaberta.pucminas.com.model

import com.google.gson.annotations.SerializedName

class Level(@SerializedName("tipo")val type: Int,
            @SerializedName("pergunta")val questions: MutableList<Question>)