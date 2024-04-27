package de.moyapro.colors.util

import android.util.*


fun <T : Any?> logAndReturn(tag: String, thing: T): T {
    Log.d(tag, "on $tag its: '$thing'")
    return thing
}
