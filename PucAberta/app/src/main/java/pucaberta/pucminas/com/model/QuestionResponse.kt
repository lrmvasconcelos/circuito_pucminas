package pucaberta.pucminas.com.model

import pucaberta.pucminas.com.model.Question

data class QuestionResponse (
        val number: Int,
        val question: Question?,
        val isCorrect: Boolean
)