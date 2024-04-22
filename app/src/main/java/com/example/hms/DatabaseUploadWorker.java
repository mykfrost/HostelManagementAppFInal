package com.example.hms;

import android.app.Service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.hms.database.DatabaseHandler;

public class DatabaseUploadWorker extends Worker {
    DatabaseHandler dbHandler ;
    public DatabaseUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Call your upload method here
        //dbHandler.uploadDatabaseToServer();
        return Result.success(); // Return success if upload is successful
        // return null;
    }
}