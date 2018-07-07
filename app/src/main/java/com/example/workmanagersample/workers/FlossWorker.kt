package com.example.workmanagersample.workers

import androidx.work.Worker

class FlossWorker : Worker() {

    override fun doWork(): Result {
        println("Started flossing teeth.")
        Thread.sleep(15_000L) // when you only have 2 teeth left, it doesn't take long!
        println("Done flossing teeth.")

        return Result.SUCCESS
    }
}
