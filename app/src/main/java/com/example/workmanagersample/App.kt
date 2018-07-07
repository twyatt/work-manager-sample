package com.example.workmanagersample

import android.app.Application
import android.preference.PreferenceManager
import androidx.work.WorkManager
import com.example.workmanagersample.data.HygienePreferences

class App : Application() {

    lateinit var preferences: HygienePreferences
    lateinit var work: WorkManagerHelper

    override fun onCreate() {
        super.onCreate()
        preferences = HygienePreferences(PreferenceManager.getDefaultSharedPreferences(this))
        work = WorkManagerHelper(WorkManager.getInstance()!!)
    }
}
