package com.example.askme.Notifications;


import android.app.Application;
import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.askme.Data.States;
import com.example.askme.Database.StatesRepository;

public class NotificationWorker extends Worker {

   private  StatesRepository mstaterepo;
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mstaterepo=StatesRepository.getStatesRepository((Application)context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        States states = mstaterepo.getStatesForNotification();
        notifications.getDailyNotification(getApplicationContext(),states);
        return Result.success();
    }
}
