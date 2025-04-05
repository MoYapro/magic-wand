package de.moyapro.colors.util

inline fun <reified T> castOrNull(value: Any?): T? {
    if (null == value) return null
    if (value is T) return value
    val typeName = T::class.simpleName ?: "Unknown"
    return if (value is T) value else null
}
