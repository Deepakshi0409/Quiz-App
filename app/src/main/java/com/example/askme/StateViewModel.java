package com.example.askme;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.askme.Data.States;
import com.example.askme.Database.StatesRepository;

public class StateViewModel extends AndroidViewModel {
    private StatesRepository statesRepository;
    public LiveData<PagedList<States>> datas;
    public StateViewModel(@NonNull Application application) {
        super(application);

        statesRepository = StatesRepository.getStatesRepository(application);
        datas = statesRepository.getAllStates();
    }
    public void inseretStates(States states) {
        statesRepository.insertStates(states);
    }
    public void deleteStates(States states){
        statesRepository.deleteStates(states);
    }
}
