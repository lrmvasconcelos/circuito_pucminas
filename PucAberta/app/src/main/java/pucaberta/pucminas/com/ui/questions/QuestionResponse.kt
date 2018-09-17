package pucaberta.pucminas.com.ui.questions

import pucaberta.pucminas.com.model.Question

data class QuestionResponse (
        val number: Int,
        val question: Question?
)