package com.example.workmanagersample.workers

import androidx.work.Worker
import com.example.workmanagersample.App
import java.lang.Thread.sleep

class BrushTeethWorker : Worker() {

    private lateinit var app: App

    override fun doWork(): Result {
        app = applicationContext as App

        println("Started brushing teeth.")
        sleep(5_000L) // 5 seconds?! no wonder we have so many cavities
        println("Done brushing teeth.")

        if (app.preferences.floss) {
            println("Time to floss!")
            (applicationContext as App).work.flossOneTime()
        } else {
            println("We'll skip flossing this time.")
        }

        return Result.SUCCESS
    }
}
