package com.hasanali.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Data, androidx.work kütüphanesinden olmalı
        val data = Data.Builder().putInt("intKey", 1).build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // internete bağlı olması gerekir ayarı
            .setRequiresCharging(false) // şarjda olmasına gerek yok ayarı
            .build()

        /*
        val myWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            //.setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5,TimeUnit.MINUTES)
            .addTag("myTag")
            .build()


        WorkManager.getInstance(this).enqueue(myWorkRequest)

         */

        val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.id).observe(this,
            Observer {
                if(it.state == WorkInfo.State.RUNNING) {
                    println("running")
                } else if(it.state == WorkInfo.State.FAILED) {
                    println("failed")
                } else if(it.state == WorkInfo.State.SUCCEEDED) {
                    println("succeeded")
                }
        })


    }



}