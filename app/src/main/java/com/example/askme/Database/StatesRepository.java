package com.example.askme.Database;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.askme.Data.States;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatesRepository {
    private StatesDao statesDao;
    private static StatesRepository statesRepository = null;
    private static int PAGE_SIZE = 15;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public StatesRepository (Application application){
        StatesDatabase db =StatesDatabase.getINSTANCE(application);
        statesDao = db.statesDao();

    }
    public static StatesRepository getStatesRepository(Application application){
        if(statesRepository == null) {
            synchronized (StatesRepository.class){
                if (statesRepository == null) {
                    statesRepository = new StatesRepository(application);
                }
            }
        } return statesRepository;
    }
    public void insertStates(final States states) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                statesDao.insertStates(states);
            }
        });

    }
    public void updateStates(final States states) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                statesDao.updateStates(states);
            }
        });
    }
    public void deleteStates(final States states) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                statesDao.deleteStates(states);
            }
        });
    }
    public LiveData<PagedList<States>> getAllStates(){
        return new LivePagedListBuilder<>(statesDao.getAllStates(),PAGE_SIZE).build();
    }
    @WorkerThread
    public States getStatesForNotification(){
        return statesDao.getStateForNotification();
    }
}
