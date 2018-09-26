package pucaberta.pucminas.com

import android.app.Application
import pucaberta.pucminas.com.model.QuestionsFile

class MyApplication : Application() {
    private lateinit var mPref: MySPreferencesManager
    override fun onCreate() {
        super.onCreate()
        instance = this
        MySPreferencesManager.initializeInstance(this)
        mPref = MySPreferencesManager.instance!!
    }

    companion object {
        lateinit var instance: MyApplication
            private set
        private const val SCORE_KEY = "score_key"
        private const val CURRENT_KEY = "current_key"
        private const val QUESTIONS_KEY = "question_key"
        private const val QUESTION_PROGRESS = "question_progress_key"
        private const val GAME_PROGRESS = "game_progress_key"
    }

    fun saveCurrentQuestion(score: Int) {
        mPref.setValue(CURRENT_KEY, score)
    }

    fun getCurrentQuestion(): Int {
        return mPref.getInt(CURRENT_KEY, 0)
    }

    fun saveScore(score: Int) {
        mPref.setValue(SCORE_KEY, score)
    }

    fun getScore(): Int {
        return mPref.getInt(SCORE_KEY, 0)
    }

    fun saveQuestions(questions: QuestionsFile) {
        mPref.saveObject(QUESTIONS_KEY, questions)
    }

    fun getQuestions(): QuestionsFile? {
        return mPref.getObject(QUESTIONS_KEY)
    }

    fun saveGameProgress(progress: Boolean) {
        mPref.setValue(GAME_PROGRESS, progress)
    }

    fun isGameInProgress(): Boolean {
        return mPref.getBoolean(GAME_PROGRESS,false)
    }

    fun isQuestionsFinished(): Boolean {
        return mPref.getBoolean(QUESTION_PROGRESS,false)
    }


    fun clearData() {
        mPref.remove(QUESTIONS_KEY)
        mPref.remove(SCORE_KEY)
        mPref.remove(GAME_PROGRESS)
        mPref.remove(QUESTION_PROGRESS)
        mPref.remove(CURRENT_KEY)
    }

    fun startQuestions(){
        mPref.setValue(QUESTION_PROGRESS, false)
    }

    fun finishQuestions() {
        mPref.setValue(QUESTION_PROGRESS, true)
    }
}