package com.example.workmanagersample.data

import android.content.SharedPreferences
import androidx.core.content.edit

private const val FLOSS = "floss"

class HygienePreferences(private val sharedPreferences: SharedPreferences) {

    var floss: Boolean
        get() = sharedPreferences.getBoolean(FLOSS, true)
        set(value) = sharedPreferences.edit {
            putBoolean(FLOSS, value)
        }
}
