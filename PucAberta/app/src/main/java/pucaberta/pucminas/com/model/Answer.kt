package pucaberta.pucminas.com.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Answer(@SerializedName("correta") val correct: Boolean,
             @SerializedName("resposta") val answer: String) : Parcelable
