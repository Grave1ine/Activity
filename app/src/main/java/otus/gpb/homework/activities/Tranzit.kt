package otus.gpb.homework.activities

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Transit(
    val name: String?,
    val secondName: String?,
    val age: String?
): Parcelable
