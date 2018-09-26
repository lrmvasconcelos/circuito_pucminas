package pucaberta.pucminas.com

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import pucaberta.pucminas.com.model.Question
import pucaberta.pucminas.com.model.QuestionsFile


class MySPreferencesManager private constructor(context: Context) {

    private val mPref: SharedPreferences

    init {
        mPref = context.getSharedPreferences(PATH, Context.MODE_PRIVATE)
    }

    fun setValue(key: String, value: String) {
        mPref.edit().putString(key, value).apply()
    }

    fun setValue(key: String, value: Boolean) {
        mPref.edit().putBoolean(key, value).apply()
    }

    fun setValue(key: String, value: Int) {
        mPref.edit().putInt(key, value).apply()
    }

    fun getString(key: String): String {
        return mPref.getString(key, "")
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mPref.getBoolean(key, defValue)
    }

    fun getInt(key: String, defValue: Int): Int {
        return mPref.getInt(key, defValue)
    }


    fun getObject(key: String): QuestionsFile? {
        val gson = Gson()
        val json = mPref.getString(key, "")
        return gson.fromJson<QuestionsFile>(json, QuestionsFile::class.java)
    }

    fun saveObject(key: String, questionsFile: QuestionsFile) {
        val gson = Gson()
        val json = gson.toJson(questionsFile)
        mPref.edit().putString(key, json).apply()
    }

    fun remove(key: String) {
        mPref.edit()
                .remove(key)
                .apply()
    }
    companion object {
        private var sInstance: MySPreferencesManager? = null
        const val PATH = "path_spref_data"
        @Synchronized
        fun initializeInstance(context: Context) {
            if (sInstance == null) {
                sInstance = MySPreferencesManager(context)
            }
        }

        val instance: MySPreferencesManager?
            @Synchronized get() {
                if (sInstance == null) {
                    initializeInstance(MyApplication.instance)
                }
                return sInstance
            }
    }
}
