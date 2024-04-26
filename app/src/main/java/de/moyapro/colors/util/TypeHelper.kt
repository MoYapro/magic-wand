package de.moyapro.colors.util

import android.util.*

inline fun <reified T> castOrNull(value: Any?): T? {
    if (null == value) return null
    if (value is T) return value
    val typeName = T::class.simpleName ?: "Unknown"
    Log.d("CAST_OR_NULL", "Could not cast $value to $typeName")
    return if (value is T) value else null
}
