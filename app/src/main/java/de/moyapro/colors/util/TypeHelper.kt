package de.moyapro.colors.util

inline fun <reified T> castOrNull(value: Any?): T? =
    if (null != value && value is T) value else null