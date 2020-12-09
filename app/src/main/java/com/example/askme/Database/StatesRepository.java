package com.example.askme.Database;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.askme.Data.States;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public LiveData<PagedList<States>> getallstates(String sortBy){
        LiveData data = new LivePagedListBuilder<>(
                statesDao.getAllSortedStates(constructQuery(sortBy)),PAGE_SIZE

        ).build();
        return data;
    }

    public SupportSQLiteQuery constructQuery(String sortBy){
        String query = "SELECT * FROM State ORDER BY" + sortBy + "ASC";
        return new SimpleSQLiteQuery(query);
    }
    @WorkerThread
    public States getStatesForNotification(){
        return statesDao.getStateForNotification();
    }

    public Future<List<States>> getQuizStates(){
        Callable<List<States>> callable = new Callable<List<States>>() {
            @Override
            public List<States> call() throws Exception {
                return statesDao.getQuizStates();
            }
        };
        return executorService.submit(callable);
    }

}

