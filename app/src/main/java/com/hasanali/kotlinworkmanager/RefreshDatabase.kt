package com.hasanali.kotlinworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDatabase(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private fun refreshDb(myNumber: Int) {
        val sharedPreferences = context.getSharedPreferences("com.hasanali.kotlinworkmanager", Context.MODE_PRIVATE)
        var mySavedNumber = sharedPreferences.getInt("myNumber", 0)
        println(mySavedNumber)

        mySavedNumber += myNumber
        sharedPreferences.edit().putInt("myNumber", mySavedNumber).apply()
    }

    override fun doWork(): Result {
        val getData = inputData
        val myNumber = getData.getInt("intKey",0)

        refreshDb(myNumber)
        return Result.success()
    }
}
