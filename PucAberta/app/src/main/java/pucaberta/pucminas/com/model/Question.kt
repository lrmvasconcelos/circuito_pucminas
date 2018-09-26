package pucaberta.pucminas.com.model

import android.os.Parcelable
import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Question(@SerializedName("enunciado") val question: String,
               @SerializedName("questoes") val answers: Array<Answer>) : Parcelable {
    fun isEmpty(): Boolean {
        return TextUtils.isEmpty(question) || answers.isEmpty()
    }
}
