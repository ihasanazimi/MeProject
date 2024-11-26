package ir.ha.meproject.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    var firstName: String,
    var lastName: String,
    var age: String,
    var fromCountry: String,
    var fromCity: String
): Parcelable